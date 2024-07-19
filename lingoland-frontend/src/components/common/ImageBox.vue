<script setup>
/*
 aspectRatio: String, 이미지 가로세로 비율
 height: Number, 높이
 width: Number, 폭
 source: String, 이미지 소스
*/
const props = defineProps({
    height: Number,
    width: Number,
    source: String,
});
</script>

<template>
    <div>
        <v-img
            :width="width"
            :height="height"
            cover
            :src="source"
            class="rounded-circle"
        ></v-img>
    </div>
</template>

<style scoped></style>
