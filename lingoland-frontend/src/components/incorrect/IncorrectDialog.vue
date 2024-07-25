<script setup>
import IncorrectList from "@/components/incorrect/IncorrectList.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue"
import GenericButton from "../common/GenericButton.vue";

import { ref } from "vue";
import { defineProps } from "vue";


const props= defineProps({
    incorrects : String,
})
const dialog = ref(false);
</script>

<template>
    <PageNavigationButton
        background-color="#BF5847"
        data="오답노트"
        source="src\assets\오답노트.png"
        @click-event="
            () => {
                dialog = true;
            }
        "
    />

    <v-dialog v-model="dialog" width="50%">
        <IncorrectList :incorrects="incorrects" />
        <div class="d-flex justify-end bg-white">
            <GenericButton
                class="ma-3"
                @click-event="
                    () => {
                        dialog = false;
                    }
                "
                data="닫기"
                :style="{
                    width: '40px',
                    backgroundColor: '#4C4637',
                    color: 'white',
                }"
            />
        </div>
    </v-dialog>
</template>

<style scoped></style>
