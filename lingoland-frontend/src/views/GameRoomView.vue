<script setup>

const memberList = ref([
  "멤버 1",
  "멤버 2",
  "멤버 3",
  "멤버 4",
  "멤버 5",
  "멤버 6",
  "멤버 7",
  "멤버 8",
  "멤버 9",
  "멤버 10",
  // 데이터 받아오기
]);

const makeQr = () => {
  //큐알 모달창 만들기이이이이이
};

const runningGame = () => {
  console.log("달리기 게임시작");
};

const writingGame = () => {
  console.log("글쓰기 게임시작");
};
</script>

<template>
   <RouterView />
</template>

<style scoped>

</style>
