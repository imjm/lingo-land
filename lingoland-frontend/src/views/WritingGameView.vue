<script setup>
import WritingInput from "@/components/writingGame/WritingInput.vue";
import WritingList from "@/components/writingGame/WritingList.vue";
</script>

<template>
    <v-layout>
        <v-main class="background-image d-flex flex-column align-center justify-center">
            <v-row>
                <div class="text-h3 text-white">
                    다음 글을 읽고 이어질 이야기를 작성하세요
                </div>
            </v-row>

            <v-row class="d-flex align-center justify-space-evenly">
                <WritingList />
                <WritingInput />
            </v-row>
        </v-main>
    </v-layout>
</template>

<style scoped>
.background-image {
    background-image: url("src\\assets\\blackboard.png");
    background-size: contain;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    width: 100%;
}
</style>
