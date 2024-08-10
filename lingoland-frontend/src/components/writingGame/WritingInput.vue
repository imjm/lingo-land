<script setup>
import { ref } from "vue";
import CountdownTimer from "./CountdownTimer.vue";
import SubmitButton from "../common/SubmitButton.vue";
import GenericInputArea from "../common/GenericInputArea.vue";

const textareaValue = ref("");

const handleTimesUp = () => {
    console.log("타이머가 종료되었습니다. 텍스트 내용:", textareaValue.value);
};
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

            <v-textarea
                class="textarea custom-textarea"
                v-model="textareaValue"
                placeholder="글을 작성해주세요"
            ></v-textarea>

            <div class="d-flex justify-end">
                <SubmitButton width="100" data="제출" class="mx-5 mb-5" />
            </div>
        </v-card>
    </div>
</template>

<style scoped>
.textarea {
    background-color: #ffffff;
    width: 90%;
    height: 100px;
}

.custom-textarea .v-input__details{
    height : 0px;
}
</style>