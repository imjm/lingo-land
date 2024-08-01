import { ref } from "vue";

const countdown = ref(3);
let timerInterval = null;
let startTime = null;

function startCountdown() {
    const interval = setInterval(() => {
        if (countdown.value > 0) {
            countdown.value--;
        } else {
            clearInterval(interval);

            startTimer(); // 카운트다운 완료 후 타이머 시작
        }
    }, 1000);
}

function startTimer() {
    startTime = Date.now();
    timerInterval = setInterval(updateTimer(startTime), 1000);
}

function updateTimer(startTime) {
    const timerElement = document.querySelector("#timer");
    const elapsedTime = Date.now() - startTime;

    const milliseconds = Math.floor((elapsedTime % 1000) / 10); // 밀리초 단위를 2자리로 표시하기 위해 10으로 나눔
    const seconds = Math.floor((elapsedTime / 1000) % 60);
    const minutes = Math.floor((elapsedTime / (1000 * 60)) % 60);

    timerElement.textContent = `${String(minutes).padStart(2, "0")}:${String(
        seconds
    ).padStart(2, "0")}:${String(milliseconds).padStart(2, "0")}`;
}

export {
    startCountdown,
    startTimer,
    updateTimer,
    countdown,
    timerInterval,
    startTime,
};
