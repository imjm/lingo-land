<script setup>
import { useTaleStore } from "@/stores/tales";

const taleStore = useTaleStore();
const { tale } = taleStore;
// import { useTaleStore } from "@/stores/tales";
//         const taleStore = useTaleStore()
//         const goTotalesList = () => {
//             taleStore.myTalesList();
//         };
//         <GenericButton
//         class="ma-5"
//         :style="{ width: '100' }"
//         data="목록보기"
//         @click-event="goTotalesList"
//         />

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
<style scoped>
</style>
