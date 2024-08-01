<template>
    <div>
        <!-- Canvas와 타이머를 포함하는 상위 div -->
        <div id="game-container">
            <canvas id="c"></canvas>
            <div id="timer">00:00:00</div>
            <div id="countdown" v-if="countdown > 0">{{ countdown }}</div>
        </div>
        <div id="coordinates" class="coordinates"></div>
        <!-- 퀴즈 문제 및 보기를 표시하는 부분 -->
        <div id="quiz-container" v-if="currentQuestion && countdown === 0">
            <h2>{{ currentQuestion.problem }}</h2>
            <ul class="no_dot d-flex justify-center">
                <li v-for="(option, index) in options" :key="index">
                    <button @click="checkAnswer(index + 1)">
                        {{ option }}
                    </button>
                </li>
            </ul>
            <p v-if="isCorrect !== null">
                {{ isCorrect ? "정답입니다!" : "틀렸습니다!" }}
            </p>
            <button @click="nextQuestion">다음 문제</button>
        </div>
        <div v-else-if="countdown === 0">
            <p>퀴즈가 완료되었습니다!</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

//초기 세팅
import {
    initDraw,
} from "@/stores/runningGame/init";

//카운트다운 & 타이머
import {
    startCountdown,
    countdown,
} from "@/stores/runningGame/time";


//문제
import {
    loadQuestions,
    checkAnswer,
    nextQuestion,
    currentQuestion,
    options,
    isCorrect,
} from "@/stores/runningGame/question";

onMounted(() => {
    startCountdown();
    initDraw();
    loadQuestions(); // 문제 로드
});


</script>

<style scoped>
.no_dot {
    list-style-type: none;
}
#game-container {
    position: relative;
    width: 100%;
    height: 100vh;
}

#c {
    width: 100%;
    height: 100%;
    display: block;
}

#timer {
    position: absolute;
    top: 10px;
    left: 10px;
    color: white;
    font-size: 20px;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 5px;
    border-radius: 5px;
}

#quiz-container {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%, -50%); /* 중앙 정렬 */
    width: 400px; /* 원하는 너비 */
    padding: 20px;
    background-color: rgba(0, 0, 0, 0.7); /* 배경 색상 */
    border-radius: 10px; /* 모서리 둥글게 */
    text-align: center; /*텍스트 중앙 정렬 */
    justify-content: center;
    color: white;
    z-index: 1000;
}

button {
    margin: 5px;
    padding: 10px 20px;
    font-size: 16px;
    color: white;
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

p {
    font-size: 18px;
    margin-top: 10px;
}
.coordinates {
    position: absolute;
    top: 10px;
    right: 10px;
    color: white;
    font-size: 20px;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 5px;
    border-radius: 5px;
}
#countdown {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 5rem;
    color: white;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 20px;
    border-radius: 10px;
}
</style>
