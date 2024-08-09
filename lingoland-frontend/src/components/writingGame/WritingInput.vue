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
    <v-card width="600">
        <CountdownTimer @timesUp="handleTimesUp" />

        <GenericInputArea v-model="textareaValue" />

        <div class="d-flex justify-end">
            <SubmitButton width="100" data="제출" />
        </div>
    </v-card>
</template>

<style scoped></style>
