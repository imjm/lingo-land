<script setup>
import { computed, defineEmits, onBeforeUnmount, onMounted } from "vue";
import { useWritingGameStore } from "@/stores/writingGame";
import { storeToRefs } from "pinia";

const emit = defineEmits(["timesUp"]);

const writingGameStore = useWritingGameStore();
const { totalTime } = storeToRefs(writingGameStore);

let timer = null;

const minutes = computed(() => {
    const mins = Math.floor(totalTime.value / 60);
    return mins < 10 ? `0${mins}` : mins;
});

const seconds = computed(() => {
    const secs = totalTime.value % 60;
    return secs < 10 ? `0${secs}` : secs;
});

const startTimer = () => {
    timer = setInterval(() => {
        if (totalTime.value > 0) {
            totalTime.value--;
        } else {
            clearInterval(timer);
            emit("timesUp"); // 타이머 종료 이벤트 발생
        }
    }, 1000);
};

onMounted(() => {
    startTimer();
});

onBeforeUnmount(() => {
    clearInterval(timer);
});
</script>

<template>
    <div class="countdown-timer">
        <div class="text-h6">남은 시간: {{ minutes }}:{{ seconds }}</div>
    </div>
</template>

<style scoped>
.countdown-timer {
    text-align: center;
    font-size: 1.2em;
}
</style>
