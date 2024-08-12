<script setup>
import WritingInput from "@/components/writingGame/WritingInput.vue";
import WritingList from "@/components/writingGame/WritingList.vue";
</script>

<template>
    <v-main class="background-image gowun-batang-regular pa-10">
        <v-row>
            <v-col class="d-flex flex-column justify-center align-center">
                <div style="font-size: 20px; color: white">
                    지금까지 쓴 글입니다.
                </div>

                <WritingList />
            </v-col>
            <v-col class="d-flex flex-column justify-center align-center">
                <WritingInput />
            </v-col>
        </v-row>
    </v-main>
</template>

<style>
.background-image {
    background-image: url("@/assets/blackboard.png");
    background-size: cover;
    background-position: center 75%;
    background-repeat: no-repeat;
    height: 100vh;
    width: 100vw;
    background-color: black;
    overflow: hidden;
}
</style>
