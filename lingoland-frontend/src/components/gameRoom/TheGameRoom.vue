<script setup>
import sampleImg from "@/assets/sampleImg.jpg";
import img1 from "@/assets/달리기.jpg";
import GameButton from "@/components/gameRoom/GameButton.vue";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import { useGameRoomStore } from "@/stores/gameRoom";
import { useUserStore } from "@/stores/user";
import { OpenVidu } from "openvidu-browser";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const userStore = useUserStore();

const gameRoomStore = useGameRoomStore();

const OV = new OpenVidu();
const session = OV.initSession();

const participants = ref([]);

// 세션에 스트림이 생성될 때 호출되는 콜백 함수
session.on("streamCreated", function (event) {
    session.subscribe(event.stream, "subscriber");
});

session.on("connectionCreated", (event) => {
    userStore.getProfileById(event.connection.data).then((info) => {
        console.log("************info", info);

        // 프로필 이미지가 없으면 기본 이미지를 넣어준다.
        if (!info.profileImage) {
            info.profileImage = sampleImg;
        }

        participants.value.push({
            connectionId: event.connection.connectionId,
            userProfile: info,
        });

        console.log("***************현재참가중인 유저", participants.value);
    });
});

session.on("connectionDestroyed", (event) => {
    const connectionId = event.connection.connectionId;

    participants.value = participants.value.filter(
        (participant) => participant.connectionId !== connectionId
    );
    console.log("************************Participant left:", connectionId);
    console.log(
        "************************All participants:",
        participants.value
    );
});

const runningGame = () => {
    console.log("달리기 게임시작");
};

const writingGame = () => {
    console.log("글쓰기 게임시작");
};

function joinRoom(sessionId) {
    gameRoomStore.getToken(sessionId).then((customToken) => {
        // 토큰을 얻어왔어
        session
            .connect(customToken)
            .then(() => {
                const publisher = OV.initPublisher("subscriber");
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

onMounted(() => {
    joinRoom(route.params.roomId);
});
</script>

<template>
    <v-main class="d-flex justify-center">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <GameMemberList :members="participants" />
                </v-col>

                <v-col cols="7">
                    <div
                        class="d-flex justify-space-evenly"
                        style="height: 30%"
                    >
                        <GameButton
                            @click-event="runningGame"
                            :img="img1"
                            name="달리기"
                            desc="문해력 문제를 맞추며 달려라!"
                        ></GameButton>
                        <GameButton
                            @click-event="writingGame"
                            :img="sampleImg"
                            name="동화만들기"
                            desc="글을 잘 쓰든 말든!!!!!!!!"
                        ></GameButton>
                    </div>

                    <div class="d-flex justify-space-evenly">
                        <div
                            class="d-flex align-center justify-center flex-column justify-space-evenly"
                            id="room-code"
                        >
                            <div>방 코드</div>
                            <div>{{ route.params.roomId }}</div>
                        </div>
                        <v-btn id="link" @click="makeQr"> URL 복사하기 </v-btn>
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
    font-weight : 600;
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
