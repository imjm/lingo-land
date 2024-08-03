<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import GenericInput from "@/components/common/GenericInput.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import Profile from "@/components/common/Profile.vue";
import RankList from "@/components/rank/RankList.vue";
import TaleList from "../tale/TaleList.vue";
</script>

<template>
    <v-main class="d-flex mt-10 justify-center">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <Profile source="src\\assets\\sampleImg.jpg" />
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
