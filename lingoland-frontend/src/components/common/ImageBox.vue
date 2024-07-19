<script setup>
/*
 aspectRatio: String, 이미지 가로세로 비율
 height: Number, 높이
 width: Number, 폭
 source: String, 이미지 소스
 setting: String, 설정
*/
const props = defineProps({
    aspectRatio: String,
    height: Number,
    width: Number,
    source: String,
    setting: String,
});
</script>

<template>
    <div>
        <v-img
            :width="width"
            :height="height"
            :aspect-ratio="aspectRatio"
            cover
            :src="source"
            :class="setting"
        ></v-img>
    </div>
</template>

<style scoped></style>
