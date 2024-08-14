<script setup>
import { useOpenviduStore } from "@/stores/openvidu";
import { useWritingGameStore } from "@/stores/writingGame";
import { storeToRefs } from "pinia";
import { ref } from "vue";
import { useRoute } from "vue-router";
import GenericInputArea from "../common/GenericInputArea.vue";
import CountdownTimer from "./CountdownTimer.vue";
import GenericInput from "../common/GenericInput.vue";

const route = useRoute();

const writingGameStore = useWritingGameStore();
const { turn, pageCount } = storeToRefs(writingGameStore);

const openviduStore = useOpenviduStore();
const { session, findCurrentStoryId, findMyLoginId } = openviduStore;

const textareaValue = ref("");
const title = ref("");

const handleTimesUp = async () => {
    // 타임아웃이므로 제출
    await submit();

    let currentStoryId = findCurrentStoryId();

    console.log("*************current storyId: ", currentStoryId);

    // 시그널 보내기
    if (turn.value + 1 < pageCount.value) {
        await session
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
        await session
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
};

function submit() {
    let myLoginId = findMyLoginId();

    const drawingDTO = {
        key: myLoginId, // 로그인 아이디
        story: textareaValue.value,
        order: Number(turn.value) + Number(1),
    };

    console.log("*******drawDTO", drawingDTO);

    // 첫번째 턴이면 제목을 제출한다.
    if (turn.value === 0) {
        // API 요청 보내기
        writingGameStore.submitTitle(route.params.roomId, title.value);
    }

    // API 요청 보내기
    writingGameStore.submitStory(route.params.roomId, drawingDTO);
}
</script>

<template>
    <div class="d-flex flex-column justify-center align-center">
        <div
            class="d-flex justify-end mb-10"
            style="font-size: 30px; color: white"
        >
            다음 글을 읽고 이어질 이야기를 작성하세요
        </div>
        <v-card
            class="d-flex flex-column justify-center align-center"
            width="500"
            height="500"
            style="background-color: #ffe280"
        >
            <CountdownTimer @timesUp="handleTimesUp" />

            <GenericInput
                v-if="turn == 0"
                hint="동화에 제목을 입력하세요"
                v-model="title"
                class="textarea"
            />

            <GenericInputArea
                class="textarea"
                v-model="textareaValue"
                placeholder="글을 작성해주세요"
            />
        </v-card>
    </div>
</template>

<style scoped>
.textarea {
    background-color: #ffffff;
    width: 90%;
    height: 100px;
}

.custom-textarea .v-input__details {
    height: 0px;
}
</style>
