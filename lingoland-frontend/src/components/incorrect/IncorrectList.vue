<script setup>
import { defineProps } from "vue";
import IncorrectListItem from "./IncorrectListItem.vue";

const props = defineProps({
    incorrects: Array,
});
</script>

<template>
    <v-list class="incorrect-list">
        <v-list-item v-for="(incorrect, index) in incorrects" :key="index">
            <IncorrectListItem :incorrect="incorrect"></IncorrectListItem>
        </v-list-item>
    </v-list>
</template>

<style scoped>
.incorrect-list {
    max-height: 50%; /* 최대 높이 설정 */
    overflow-y: auto; /* 수직 스크롤을 가능하게 설정 */
}
/* width */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px grey;
    border-radius: 10px;
}

/* Handle */
::-webkit-scrollbar-thumb {
    background: rgb(252, 194, 85);
    border-radius: 10px;
}
</style>
