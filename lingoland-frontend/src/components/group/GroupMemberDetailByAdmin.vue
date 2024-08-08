<script setup>
import Profile from "@/components/common/Profile.vue";
import TaleList from "../tale/TaleList.vue";

import { ref } from "vue";
import IncorrectDialog from "../incorrect/IncorrectDialog.vue";

const incorrectList = ref([
    "문제 1",
    "문제 2",
    "문제 3",
    "문제 4",
    "문제 5",
    "문제 1",
    "문제 2",
    "문제 3",
    "문제 4",
    "문제 5",
    // 데이터 받아오기
]);
</script>

<template>
    <v-main class="d-flex mt-10 justify-center">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <v-row>
                        <Profile />
                    </v-row>
                    <v-row>
                        <v-col>
                            <IncorrectDialog :incorrects="incorrectList" />
                        </v-col>
                    </v-row>
                </v-col>

                <v-col cols="7">
                    <TaleList />
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>

<style scoped>
.room-code {
    background-color: #d2f0ff;
    width: 100%;
    height: 300px;
    border-radius: 4px;
    font-size: x-large;
}
</style>
