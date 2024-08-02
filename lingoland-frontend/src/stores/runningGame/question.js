import { ref } from "vue";

const questions = ref([]);
const currentQuestion = ref(null);
const options = ref([]);
const isCorrect = ref(null);
let answer = null;
let answerTimeout = null;
let closeQuestionTimeout = null;
const shownQuestions = new Set(); // 이미 표시된 문제를 추적
let index = 0;

const questionCountDown = ref(5);

function qcountdown() {
    questionCountDown.value = 5;

    const interval = setInterval(() => {
        if (questionCountDown.value > 0) {
            questionCountDown.value--;
        } else {
            clearInterval(interval);
        }
    }, 1000);
}

function loadQuestions() {
    fetch("/problem.json") // JSON 파일 경로
        .then((response) => response.json())
        .then((data) => {
            questions.value = data;
            console.log(data);
        })
        .catch((error) => {
            console.error("문제 로드 실패:", error);
        });
}

function updateQuestion() {
    if (index >= 0 && index < questions.value.length) {
        if (!currentQuestion.value) {
            //     // 이미 표시된 문제인지 확인하고 현재 문제가 없는지 확인
            loadQuestion();
            console.log(index);
            shownQuestions.add(index); // 표시된 문제로 기록
        }
    } else {
        questions.value = null;
    }
}

function loadQuestion() {
    if (questions.value.length > 0 && index < questions.value.length) {
        currentQuestion.value = questions.value[index];
        options.value = [
            currentQuestion.value["1"],
            currentQuestion.value["2"],
            currentQuestion.value["3"],
        ];
        isCorrect.value = null; // 초기화
        // if (answerTimeout) clearTimeout(answerTimeout);
        // if (closeQuestionTimeout) clearTimeout(closeQuestionTimeout);
    }
    index++;
    qcountdown();

    answerTimeout = setTimeout(() => {
        checkAnswerAndTime();
    }, 5000);
}

function checkAnswer(selected) {
    if (currentQuestion.value === null) return;

    console.log("정답 : ", currentQuestion.value.answer);
    console.log("내가 고른 거 : ", selected);
    //정답 여부를 표시
    answer = selected;
}

function checkAnswerAndTime() {
    console.log("나는 불리는 중입니다!");
    isCorrect.value = answer == currentQuestion.value.answer;
    currentQuestion.value = null; // 문제 창 닫기
    closeQuestionTimeout = setTimeout(() => {
        isCorrect.value = null; // 정답 여부 초기화
        resetQuestionOnExit();
    }, 2000);
    // 정답 여부 표시 후 2초 뒤에 문제 창 닫기
}

function resetQuestionOnExit() {
    if (answerTimeout) clearTimeout(answerTimeout);
    if (closeQuestionTimeout) clearTimeout(closeQuestionTimeout);
}

export {
    loadQuestions,
    loadQuestion,
    checkAnswer,
    questions,
    currentQuestion,
    options,
    isCorrect,
    updateQuestion,
    resetQuestionOnExit,
    questionCountDown, // 새로운 함수 내보내기
};
