<script setup></script>

<template>
    <v-carousel
        class="pa-5"
        height="500"
        show-arrows="hover"
        cycle
        hide-delimiter-background
    >
        <v-carousel-item>
            <img
                src="\src\assets\달리기.jpg"
                width="100%"
                height="80%"
                margin-bottom="10px"
            />
            <div>달리기 게임 설명</div>
        </v-carousel-item>

        <v-carousel-item>
            <img src="\src\assets\sampleImg.jpg" width="100%" height="80%" />
            <div>릴레이 글쓰기 게임 설명</div>
        </v-carousel-item>
    </v-carousel>
</template>
<script></script>

<style scoped></style>
