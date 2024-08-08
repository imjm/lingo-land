<script setup>
import Profile from "@/components/common/Profile.vue";
import TaleList from "../tale/TaleList.vue";
</script>

<template>
    <v-main class="d-flex mt-10 justify-center">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <Profile />
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
