import { ref } from "vue";

const questions = ref([]);
const currentIndex = ref(0);
const currentQuestion = ref(null);
const options = ref([]);
const isCorrect = ref(null);

function loadQuestions() {
    fetch("problem.json") // JSON 파일 경로
        .then((response) => response.json())
        .then((data) => {
            questions.value = data;
            loadQuestion(); // 첫 문제 로드
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
    }
}

//얘가 계속 불리는 중
function checkAnswer(selected) {
    if(currentQuestion.value === null) return;
    isCorrect.value = selected === currentQuestion.value.answer;
}

function nextQuestion() {
    currentIndex.value++;
    if (currentIndex.value < questions.value.length) {
        loadQuestion();
    } else {
        currentQuestion.value = null; // 퀴즈 완료
    }
}

export {
    loadQuestions,
    loadQuestion,
    checkAnswer,
    nextQuestion,
    questions,
    currentIndex,
    currentQuestion,
    options,
    isCorrect,
};
