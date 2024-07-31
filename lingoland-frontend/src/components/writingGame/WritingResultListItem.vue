<script setup>
import { defineProps } from "vue";

const props = defineProps({
    // title : String,
    source: String,
    text: String,
});
</script>

<template>
    <v-carousel-item>
        <v-row no-gutters class="ma-5">
            <v-col class="ma-5">
                <img :src="source" width="100%" />
            </v-col>
            <v-col class="ma-5">
                <h1>제목 집에 가고 싶어요</h1>
                <div>{{ text }}</div>
            </v-col>
        </v-row>
    </v-carousel-item>
</template>

<style scoped></style>
