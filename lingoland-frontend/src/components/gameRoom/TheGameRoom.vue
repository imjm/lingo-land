<script setup>
import sampleImg from "@/assets/sampleImg.jpg";
import runningImg from "@/assets/달리기.jpg";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import { useOpenviduStore } from "@/stores/openvidu";
import { useWritingGameStore } from "@/stores/writingGame";
import { storeToRefs } from "pinia";
import Swal from "sweetalert2";
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import NameTag from "../common/NameTag.vue";

window.Swal = Swal;

const route = useRoute();
const router = useRouter();

const openviduStore = useOpenviduStore();
const writingGameStore = useWritingGameStore();

const { pageCount } = storeToRefs(writingGameStore);
const { session } = openviduStore;

const startRunningGame = () => {
    // 방장인지 아닌지 확인해야함

    // 달리기 게임 시작 시그널 송신
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
    // 방장인지 아닌지 확인해야함

    if (!pageCount.value || pageCount.value <= 0) {
        Swal.fire({
            title: "한 페이지 이상으로 입력하세요",
            icon: "error",
        });

        return;
    }
    if (pageCount.value > 10) {
        Swal.fire({
            title: "10 페이지 이하로 입력하세요",
            icon: "error",
        });
        pageCount.value = 10;

        return;
    }

    // 글쓰기 게임 시작 서버로 API 호출
    writingGameStore.setWritingGame(route.params.roomId, {
        numPart: openviduStore.participants.value.length,
        maxTurn: pageCount.value,
    });

    // 글쓰기 게임 시작 시그널 송신
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

// 방에서 나가기
function outSession() {
    // 세션과의 연결 종료
    session.disconnect();
    // 메인페이지로 리다리엑트
    router.replace({ name: "mainPage" }).then(() => {
        openviduStore.participants.value = ref([]);
    });
}
</script>

<template>
    <v-main class="d-flex justify-center mt-10">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <NameTag data="플레이어"></NameTag>
                    <GameMemberList
                        :members="openviduStore.participants"
                        :style="{ height: '90vh' }"
                    />
                </v-col>

                <v-col cols="7">
                    <div
                        class="d-flex justify-space-evenly"
                        style="height: 65vh"
                    >
                        <v-card
                            class="d-flex justify-center align-start"
                            width="40%"
                            height="90%"
                            border-radius="1%"
                            box-shadow="7px 7px 5px rgba(0, 0, 0, 0.25)"
                            @click="startRunningGame"
                        >
                            <v-row
                                class="d-flex justify-center align-center flex-column"
                            >
                                <v-col>
                                    <v-img
                                        class="ma-3 mt-10"
                                        :src="runningImg"
                                    ></v-img>
                                </v-col>
                                <v-col
                                    class="d-flex justify-center align-center"
                                >
                                    <div height="10%">달리기</div>
                                </v-col>
                                <v-col
                                    class="d-flex justify-center align-center"
                                >
                                    <div style="font-size: large">
                                        문해력 문제를 맞추며 달려라!
                                    </div>
                                </v-col>
                            </v-row>
                        </v-card>

                        <v-card
                            class="d-flex justify-center align-start"
                            width="40%"
                            height="90%"
                            border-radius="1%"
                            box-shadow="7px 7px 5px rgba(0, 0, 0, 0.25)"
                        >
                            <v-row
                                class="d-flex justify-center align-center flex-column"
                            >
                                <v-col>
                                    <v-img
                                        class="ma-3 mt-10"
                                        :src="sampleImg"
                                    ></v-img>
                                </v-col>
                                <v-col
                                    class="d-flex justify-center align-center"
                                >
                                    <div height="10%">동화만들기</div>
                                </v-col>
                                <v-col
                                    class="d-flex justify-center align-center"
                                >
                                    <div style="font-size: large">
                                        글을 잘 쓰든 말든!!!!!!!!
                                    </div>
                                </v-col>
                                <v-col>
                                    <GenericInput
                                        v-model="pageCount"
                                        hint="페이지 수를 입력하세요"
                                        class="mx-2"
                                        type="number"
                                        min="1"
                                        max="10"
                                    />
                                </v-col>
                                <v-col
                                    class="d-flex justify-center align-center"
                                >
                                    <GenericButton
                                        width="90%"
                                        data="시작하기"
                                        @click-event="startWritingGame"
                                    />
                                </v-col>
                            </v-row>
                        </v-card>
                    </div>

                    <div class="d-flex justify-space-evenly">
                        <div
                            class="d-flex align-center justify-center flex-column"
                            id="room-code"
                        >
                            <h4>방 코드</h4>
                            <h4>{{ route.params.roomId }}</h4>
                        </div>
                        <v-btn
                            id="link"
                            @click="writeClipboardText(route.params.roomId)"
                        >
                            URL 복사하기
                        </v-btn>
                    </div>

                    <div class="d-flex justify-space-evenly mt-10">
                        <v-btn id="out" @click="outSession"> 나가기 </v-btn>
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

#out {
    width: 85%;
    height: 100px;
    font-size: x-large;
    font-weight: 600;
    background-color: #9e9e9e;
    border-radius: 1%;
}
</style>
