<script setup>
import WritingInput from "@/components/writingGame/WritingInput.vue";
import WritingList from "@/components/writingGame/WritingList.vue";
</script>

<template>
    <v-main class="background-image gowun-batang-regular pa-10">
        <v-row>
            <!-- 글 리스트 -->
            <v-col cols="6" class="image d-flex flex-column justify-start align-start">
                <div class="scroll">
                    <div class="story" v-for="story in myStoryOrdering" :key="story">
                        <div style="font-weight: 700; width : 80%">{{ story }}</div>
                    </div>
                </div>
            </v-col>

            <!-- 글쓰는 곳 -->
            <v-col cols="6" class="d-flex flex-column align-center" height="100vh">
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

.scroll {
    position: absolute;
    top: 10%;
    max-height: 70%;
    overflow-y: auto;
    overflow-x: hidden;
    width: 80%;

}

.image {
    background-image: url("/balloon.png");
    height: 95vh;
    width: 95vw;
    background-size: cover;
    background-position: center;
    position: relative;
    bottom: 25px;
    left: 35px;
}

.story {
    position: relative;
    top: 10%;
    left: 20%;
    margin-bottom: 20px;
}

.scroll::-webkit-scrollbar {
    width: 5px;
    /* 스크롤바의 너비 */
}

.scroll::-webkit-scrollbar-track {
    background: rgb(255, 255, 255, 1);
}

.scroll::-webkit-scrollbar-thumb {
    background: #c2c2c2;
    /* 핸들의 색상 */
    border-radius: 10px;
    /* 핸들의 모서리 둥글기 */
}
</style>
