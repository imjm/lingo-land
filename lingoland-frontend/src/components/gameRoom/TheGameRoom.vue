<script setup>
import sampleImg from "@/assets/sampleImg.jpg";
import img1 from "@/assets/달리기.jpg";
import GameButton from "@/components/gameRoom/GameButton.vue";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import { useGameRoomStore } from "@/stores/gameRoom";
import { useOpenviduStore } from "@/stores/openvidu";
import { storeToRefs } from "pinia";
import swal from "sweetalert2";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

window.Swal = swal;

const route = useRoute();

const openviduStore = useOpenviduStore();
const gameRoomStore = useGameRoomStore();

const { OV, session } = openviduStore;
const { participants } = storeToRefs(openviduStore);

const startRunningGame = () => {
  // 시그널 송신
  session
    .signal({
      type: "gameStart",
      data: JSON.stringify({ type: 1, data: "running game" }),
    })
    .then(() => {
      console.log("******************Game start running signal sent");
    })
    .catch((error) => {
      console.error("****************Error sending signal:", error);
    });
};

const startWritingGame = () => {
  // 시그널 송신
  session
    .signal({
      type: "gameStart",
      data: JSON.stringify({ type: 2, data: "writing game" }),
    })
    .then(() => {
      console.log("******************Game start writing signal sent");
    })
    .catch((error) => {
      console.error("****************Error sending signal:", error);
    });
};

function joinRoom(sessionId) {
  gameRoomStore.getToken(sessionId).then((customToken) => {
    // 토큰을 얻어왔어
    session
      .connect(customToken)
      .then(() => {
        const publisher = OV.initPublisher("publisher");
        session.publish(publisher);
      })
      .catch((error) => {
        console.error("There was an error connecting to the session:", error);
      });
  });
}

// 방코드 복사하기
async function writeClipboardText(text) {
  try {
    await navigator.clipboard.writeText(text);
    Swal.fire({
      title: "방 코드가 복사되었습니다.",
      icon: "success",
      confirmButtonText: "완료",
    });
  } catch (error) {
    console.error(error.message);
  }
}

onMounted(() => {
  joinRoom(route.params.roomId);
});
</script>

<template>
  <v-main class="d-flex justify-center mt-10">
    <v-container>
      <v-row>
        <v-col cols="5">
          <GameMemberList :members="participants" :style="{ height: '90vh' }" />
        </v-col>

        <v-col cols="7">
          <div class="d-flex justify-space-evenly" style="height: 65vh">
            <GameButton
              @click-event="startRunningGame"
              :img="img1"
              name="달리기"
              desc="문해력 문제를 맞추며 달려라!"
            ></GameButton>

            <GameButton
              @click-event="startWritingGame"
              :img="sampleImg"
              name="동화만들기"
              desc="글을 잘 쓰든 말든!!!!!!!!"
            ></GameButton>
          </div>

          <div class="d-flex justify-space-evenly">
            <div
              class="d-flex align-center justify-center flex-column"
              id="room-code"
            >
              <h4>방 코드</h4>
              <h4>{{ route.params.roomId }}</h4>
            </div>
            <v-btn id="link" @click="writeClipboardText(route.params.roomId)">
              URL 복사하기
            </v-btn>
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
  font-weight: 600;
  background-color: #cccbff;
  border-radius: 1%;
}

#room-code {
  width: 40%;
  height: 150px;
  background-color: #d2f0ff;
  border-radius: 1%;
}
</style>
