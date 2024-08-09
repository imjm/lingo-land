import axios from "axios";
import { storeToRefs } from "pinia";
import { ref } from "vue";
import { useOpenviduStore } from "../openvidu";
import { coinScore } from "./init";

const { VITE_SERVER_URL } = import.meta.env;

const questions = ref([]);
const currentQuestion = ref(null);
const options = ref([]);
const isCorrect = ref(null);
const gameRanks = ref([]);

const openviduStore = useOpenviduStore();
const { session } = openviduStore;
const { reparticipants } = storeToRefs(openviduStore);
for (const participant of reparticipants.value) {
  gameRanks.value.push({
    connectionId: participant.connectionId,
    userId: participant.userId,
    score: 0,
  });
}
let answer = null;
let answerTimeout = null;
let closeQuestionTimeout = null;
const shownQuestions = new Set(); // 이미 표시된 문제를 추적
let index = 0;

// **openvidu signal
// 문제를 정답을 확인했다. -> 시그널을 보낸다.
const checkProblem = () => {
  session
    .signal({
      type: "checkProblem",
      data: JSON.stringify({
        score: isCorrect.value ? 1 : 0, // 점수
        isCorrect: isCorrect.value, // 문제를 맞췄다. 틀렸다. (T/F)
        answer: answer, // 내가 작성한 답
      }),
    })
    .then(() => {
      // console.log("**********************문제를 풀었다 시그널");
    });
};

// 문제 정답 확인에 대한 시그널을 수신하는 함수
session.on("signal:checkProblem", (event) => {
  const problemResult = JSON.parse(event.data);

  // participants에서의 커넥션아이디와
  // event.from.connectionId가 같은 놈을 찾아서

  // 점수를 더해주면 된다.
  let lenPar = reparticipants.value.length;
  for (let i = 0; i < lenPar; i++) {
    if (gameRanks.value[i].connectionId === event.from.connectionId) {
      gameRanks.value[i].score += problemResult.score;
      gameRanks.value[i].score += coinScore.value;
      coinScore.value = 0
      break;
    }
  }
  // console.log("****************전체 participants", participants.value);
  console.log(
    // "****************시그널보낸애 connectionId",
    event.from.connectionId
  );
  // console.log("****************problemResult", problemResult);
  console.log("***************시그널 보낸애 점수 점수점수", gameRanks.value);
  console.log("점수점수 refcoinScore",coinScore.value)
});

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

async function loadQuestions() {
  await axios({
    url: `${VITE_SERVER_URL}/problems`,
    method: "get",
    withCredentials: true,
  })
    .then((response) => {
      console.log(response.data.problems);

      questions.value = response.data.problems;
      //   console.log(22222, questions.value);
    })
    .catch((error) => {
      console.error("문제 로드 실패:", error);
    });
}
// function loadQuestions() {
//     fetch("/problem.json") // JSON 파일 경로
//         .then((response) => response.json())
//         .then((data) => {
//             questions.value = data;
//             console.log(data);
//         })
//         .catch((error) => {
//             console.error("문제 로드 실패:", error);
//         });
// }

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

  // console.log("정답 : ", currentQuestion.value.answer);
  // console.log("내가 고른 거 : ", selected);
  //정답 여부를 표시
  answer = selected;
}

function checkAnswerAndTime() {
  console.log("나는 불리는 중입니다!");
  isCorrect.value = answer == currentQuestion.value.answer;
  currentQuestion.value = null; // 문제 창 닫기
  closeQuestionTimeout = setTimeout(() => {
    checkProblem(); // 문제를 풀었을 때 시그널을 보낸다.
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
  checkAnswer,
  currentQuestion,
  isCorrect,
  loadQuestion,
  loadQuestions,
  options,
  questionCountDown,
  questions,
  resetQuestionOnExit,
  updateQuestion,
  gameRanks,
};
