<script setup>
import { useOpenviduStore } from "@/stores/openvidu";
import { useWritingGameStore } from "@/stores/writingGame";
import { storeToRefs } from "pinia";
import { ref } from "vue";
import { useRoute } from "vue-router";
import GenericInputArea from "../common/GenericInputArea.vue";
import SubmitButton from "../common/SubmitButton.vue";
import CountdownTimer from "./CountdownTimer.vue";

const route = useRoute();

const writingGameStore = useWritingGameStore();
const { turn, pageCount } = storeToRefs(writingGameStore);

const openviduStore = useOpenviduStore();
const { session, findCurrentStoryId, findMyLoginId } = openviduStore;

const textareaValue = ref("");
const isSubmit = ref(false);

const handleTimesUp = () => {
    // 타임아웃이므로 강제로 제출
    // 아직 제출하지 않았으면 제출시킨다.
    if (!isSubmit.value) {
        sumbit();
    }

    isSubmit.value = false;

    let currentStoryId = findCurrentStoryId();

    console.log("*************current storyId: ", currentStoryId);

    // 시그널 보내기
    if (turn.value + 1 < pageCount.value) {
        session
            .signal({
                type: "writingGame",
                data: JSON.stringify({
                    storyId: currentStoryId,
                    story: textareaValue.value,
                }),
            })
            .then(() => {
                console.log("******************writingGame submit");
            })
            .catch((error) => {
                console.error("***************error sending signal", error);
            });
    } else {
        // 마지막 페이지일 때 글쓰기 게임 종료
        session
            .signal({ type: "gameEnd", data: JSON.stringify({ type: 2 }) })
            .then(() => {
                console.log("***********************writingGame end");
            })
            .catch((error) => {
                console.error("******************error sending signal", error);
            });
    }

    // textarea 초기화하기
    textareaValue.value = "";

    // turn 증가하기
    turn.value++;
};

function sumbit() {
    let myLoginId = findMyLoginId();

    const drawingDTO = {
        key: myLoginId, // 로그인 아이디
        story: textareaValue.value,
        order: Number(turn.value) + Number(1),
    };

    console.log("*******drawDTO", drawingDTO);
    // API 요청 보내기
    writingGameStore.submitStory(route.params.roomId, drawingDTO);

    isSubmit.value = true;
}
</script>

<template>
    <v-card width="600">
        <CountdownTimer @timesUp="handleTimesUp" />

        <GenericInputArea v-model="textareaValue" />
    </v-card>
</template>

<style scoped>
.textarea {
    background-color: #537960;
    width: 90%;
    height: 100px;
}

.custom-textarea .v-input__details{
    height : 0px;
}
</style>