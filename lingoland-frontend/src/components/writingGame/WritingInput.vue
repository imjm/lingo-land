<template>
    <v-card width="400">
        <CountdownTimer @timesUp="handleTimesUp" />
        <v-textarea
            v-model="textareaValue"
            font-size="1.2em"
            variant="outlined"
        ></v-textarea>
        <div class="d-flex justify-end">
            <SubmitButton width="100" data="제출" />
        </div>
    </v-card>
</template>

<script setup>
import { ref } from "vue";
import CountdownTimer from "./CountdownTimer.vue";
import SubmitButton from "../common/SubmitButton.vue";

const textareaValue = ref("");

const handleTimesUp = () => {
    console.log("타이머가 종료되었습니다. 텍스트 내용:", textareaValue.value);
};
</script>

<style scoped>

</style>
