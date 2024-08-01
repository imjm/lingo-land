import { ref } from "vue";

const questions = ref([]);
const currentIndex = ref(0);
const currentQuestion = ref(null);
const options = ref([]);
const isCorrect = ref(null);
let answerTimeout = null;
let closeQuestionTimeout = null;
const shownQuestions = new Set(); // 이미 표시된 문제를 추적

function loadQuestions() {
    fetch("/problem.json") // JSON 파일 경로
        .then((response) => response.json())
        .then((data) => {
            questions.value = data;
        })
        .catch((error) => {
            console.error("문제 로드 실패:", error);
        });
}

function loadQuestion() {
    if (
        questions.value.length > 0 &&
        currentIndex.value < questions.value.length
    ) {
        currentQuestion.value = questions.value[currentIndex.value];
        options.value = [
            currentQuestion.value["1"],
            currentQuestion.value["2"],
            currentQuestion.value["3"],
        ];
        isCorrect.value = null; // 초기화
        if (answerTimeout) clearTimeout(answerTimeout);
        if (closeQuestionTimeout) clearTimeout(closeQuestionTimeout);
    }
}

function checkAnswer(selected) {
    if (currentQuestion.value === null) return;

    // 5초 후에 정답 여부를 표시
    answerTimeout = setTimeout(() => {
        isCorrect.value = selected === currentQuestion.value.answer;

        // 정답 여부 표시 후 3초 뒤에 문제 창 닫기
        closeQuestionTimeout = setTimeout(() => {
            currentQuestion.value = null; // 문제 창 닫기
            isCorrect.value = null; // 정답 여부 초기화
        }, 3000);
    }, 5000); // 5초 후에 정답 표시
}

function updateQuestionBasedOnZ(zCoordinate) {
    if (zCoordinate < 1000) {
        return; // 1000 미만에서는 퀴즈를 표시하지 않음
    }

    const index = Math.floor(zCoordinate / 1000) - 1; // 0-based index for each 1000 units of z
    if (index >= 0 && index < questions.value.length) {
        if (!shownQuestions.has(index) && !currentQuestion.value) { // 이미 표시된 문제인지 확인하고 현재 문제가 없는지 확인
            currentIndex.value = index;
            loadQuestion();
            shownQuestions.add(index); // 표시된 문제로 기록
        }
    }
}

function resetQuestionOnExit(zCoordinate) {
    const index = Math.floor(zCoordinate / 1000) - 1;
    if (currentQuestion.value && !shownQuestions.has(index)) {
        currentQuestion.value = null;
        isCorrect.value = null;
        if (answerTimeout) clearTimeout(answerTimeout);
        if (closeQuestionTimeout) clearTimeout(closeQuestionTimeout);
    }
}

export {
    loadQuestions,
    loadQuestion,
    checkAnswer,
    questions,
    currentIndex,
    currentQuestion,
    options,
    isCorrect,
    updateQuestionBasedOnZ,
    resetQuestionOnExit, // 새로운 함수 내보내기
};
