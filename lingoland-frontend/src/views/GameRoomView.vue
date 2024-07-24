<script setup>
import { ref } from "vue";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import GameButton from "@/components/gameRoom/GameButton.vue";

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
  <v-main class="d-flex justify-center">
    <v-container >
      <h1>GameRoomView</h1>

      <v-row>
        <v-col cols="5">
          <GameMemberList :members="memberList" />
        </v-col>

        <v-col cols="7">
          <div class="d-flex justify-space-evenly" style="height: 30%">
            <GameButton
              @click-event="runningGame"
              img="src/assets/달리기.jpg"
              name="달리기"
              desc="문해력이 딸리든 뭐든!!!!!! 일단 달려 볼까!!!!!!!!"
            ></GameButton>
            <GameButton
              @click-event="writingGame"
              img="src/assets/sampleImg.jpg"
              name="글쓰기"
              desc="글을 잘 쓰든 말든!!!!!!!!"
            ></GameButton>
          </div>

          <div class="d-flex justify-space-evenly">
            <div
              class="d-flex align-center justify-center flex-column justify-space-evenly"
              id="room-code"
            >
              <div>방 코드</div>
              <div>1234-5678</div>
            </div>
            <v-btn id="link" @click="makeQr"> QR 생성하기 </v-btn>
          </div>
        </v-col>
      </v-row>
  </v-container>
  </v-main>
</template>

<style scoped>
#link {
  width: 40%;
  height: 150px;
  font-size: x-large;
  background-color: #cccbff;
  border-radius: 1%;
}

#room-code {
  width: 40%;
  height: 150px;
  font-size: large;
  background-color: #d2f0ff;
  border-radius: 1%;
}
</style>
