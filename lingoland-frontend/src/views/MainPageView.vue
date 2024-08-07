<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import GenericInput from "@/components/common/GenericInput.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import Profile from "@/components/common/Profile.vue";
import RankList from "@/components/rank/RankList.vue";
import { useGameRoomStore } from "@/stores/gameRoom";
import { useGameStore } from "@/stores/runningGame/gameStore";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const gameStore = useGameStore();
const gameRoomStore = useGameRoomStore();
const router = useRouter();

const roomCode = ref("");

function makeRoom() {
    const sessionPromise = gameRoomStore.getSession();

    sessionPromise.then((sessionId) => {
        router.push({
            name: "gameRoom",
            params: { roomId: sessionId },
        });
    });
}

function joinRoom() {
    router.push({
        name: "gameRoom",
        params: { roomId: roomCode.value },
    });
}

onMounted(() => {
    const reesult = {
        problemList: [
            {
                problemId: 1,
                answer: 2,
            },
            {
                problemId: 2,
                answer: 2,
            },
            {
                problemId: 3,
                answer: 3,
            },
            {
                problemId: 4,
                answer: 2,
            },
            {
                problemId: 5,
                answer: 3,
            },
        ],
    };

    gameStore.saveResult(reesult);
});
</script>

<template>
    <v-main class="d-flex justify-center mt-10" height="100vh">
        <v-container>
            <v-row>
                <v-col cols="5" class="d-flex justify-center" height="100vh">
                    <Profile />
                </v-col>

                <v-col cols="7">
                    <v-row>
                        <v-col
                            cols="6"
                            class="d-flex flex-column align-content-space-between"
                        >
                            <div>
                                <PageNavigationButton
                                    background-color="#CCCBFF"
                                    data="방 만들기"
                                    @click-event="makeRoom"
                                />
                            </div>

                            <div
                                class="room-code d-flex flex-column justify-center align-center"
                            >
                                <div class="ma-3 text-h4 font-weight-black">
                                    방 코드
                                </div>
                                <GenericInput
                                    v-model="roomCode"
                                    :style="{ width: '90%' }"
                                />
                                <GenericButton
                                    @click-event="joinRoom"
                                    data="입력"
                                />
                            </div>
                        </v-col>

                        <v-col cols="6">
                            <RankList />
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>

<style scoped>
.room-code {
    background-color: #d2f0ff;
    width: 100%;
    height: 300px;
    border-radius: 4px;
    font-size: x-large;
}
</style>
