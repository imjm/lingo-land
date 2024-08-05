<script setup>
import sampleImg from "@/assets/sampleImg.jpg";
import img1 from "@/assets/달리기.jpg";
import GameButton from "@/components/gameRoom/GameButton.vue";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import { useGameRoomStore } from "@/stores/gameRoom";
import { useUserStore } from "@/stores/user";
import { OpenVidu } from "openvidu-browser";
import swal from "sweetalert2";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

window.Swal = swal;

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const gameRoomStore = useGameRoomStore();

const OV = new OpenVidu();
const session = OV.initSession();

const participants = ref([]);

// 세션에 스트림이 생성될 때 호출되는 콜백 함수
session.on("streamCreated", function (event) {
    session.subscribe(event.stream, "subscriber");
});

// 세션에 새로운 유저가 참가하면 호출되는 콜백함수
session.on("connectionCreated", (event) => {
    userStore.getProfileById(event.connection.data).then((info) => {
        // 참가자 배열에 이미 있는 사람인지 확인 필요
        // 배열에서 userId 중복되는지 확인
        const exists = participants.value.some(
            (participant) => participant.userId === event.connection.data
        );

        // 중복되지 않으면 배열에 추가
        if (!exists) {
            // 프로필 이미지가 없으면 기본 이미지를 넣어준다.
            if (!info.profileImage) {
                info.profileImage = sampleImg;
            }

            participants.value.push({
                connectionId: event.connection.connectionId,
                userId: event.connection.data,
                userProfile: info,
            });
        }
    });
});

session.on("connectionDestroyed", (event) => {
    const connectionId = event.connection.connectionId;

    participants.value = participants.value.filter(
        (participant) => participant.connectionId !== connectionId
    );
});

// Signal 수신 처리
session.on("signal:gameStart", function (event) {
    const gameType = JSON.parse(event.data);

    if (gameType.type === 1) {
        // 달리기 게임으로
        router.replace({ name: "runningGame" });
    } else if (gameType.type === 2) {
        // 글쓰기 게임으로
        router.replace({ name: "writingGame" });
    }
});

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
    // 시그널 수신
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
                console.error(
                    "There was an error connecting to the session:",
                    error
                );
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
                    <GameMemberList
                        :members="participants"
                        :style="{ height: '90vh' }"
                    />
                </v-col>

                <v-col cols="7">
                    <div
                        class="d-flex justify-space-evenly"
                        style="height: 65vh"
                    >
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
                        <v-btn
                            id="link"
                            @click="writeClipboardText(route.params.roomId)"
                        >
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
