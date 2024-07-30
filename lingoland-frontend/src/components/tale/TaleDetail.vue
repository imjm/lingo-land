<script setup>
import { useTaleStore } from "@/stores/tales";
import WritingResultListItem from "../writingGame/WritingResultListItem.vue";
import { defineProps } from "vue";

const taleStore = useTaleStore();
const { tale } = taleStore;


//= title cover summary content(list);


</script>



<template>
    <v-carousel hide-delimiter-background progress="primary" hide-delimiters>
        <v-carousel-item v-for="t in tale.content" :key="t">
            <v-row no-gutters class="ma-5">
                <v-col class="ma-5">
                    <img :src="t.illustration" width="100%" />
                </v-col>
                <v-col class="ma-5">
                    <div>{{ t.story }}</div>
                </v-col>
            </v-row>
        </v-carousel-item>
    </v-carousel>
</template>
<style scoped></style>
