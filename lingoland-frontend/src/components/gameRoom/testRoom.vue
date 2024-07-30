<script setup>
import { OpenVidu } from "openvidu-browser";
import { onMounted } from "vue";
import { useGameRoomStore } from "@/stores/gameRoom";
import { useRoute } from "vue-router";

const route = useRoute();

const gameRoomStore = useGameRoomStore();

const OV = new OpenVidu();
const session = OV.initSession();

// 세션에 스트림이 생성될 때 호출되는 콜백 함수
session.on("streamCreated", function (event) {
    session.subscribe(event.stream, "video-container");
});

function joinRoom(sessionId) {
    gameRoomStore.getToken(sessionId).then((tokenId) => {
        // 토큰을 얻어왔어
        session
            .connect(tokenId)
            .then(() => {
                const publisher = OV.initPublisher("video-container");
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
    // console.log(route.params.roomId);
    joinRoom(route.params.roomId);
});
</script>

<template>
    <div id="video-container"></div>
</template>

<style scoped></style>
