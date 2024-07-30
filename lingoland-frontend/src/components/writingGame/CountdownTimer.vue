<template>
    <div class="countdown-timer">
      <div class="text-h6">남은 시간: {{ minutes }}:{{ seconds }}</div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
  
  const totalTime = ref(120); // 2분 = 120초
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
  
  <style scoped>
  .countdown-timer {
    text-align: center;
    font-size: 1.2em;
  }
  </style>
  