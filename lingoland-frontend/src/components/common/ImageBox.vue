<script setup>
import { defineProps } from "vue";
/*
 aspectRatio: String, 이미지 가로세로 비율
 height: Number, 높이
 width: Number, 폭
 source: String, 이미지 소스
 
*/

const props = defineProps({
  source: String,
});

</script>

<template>
  <div>
    <v-img
      :src="source"
      width="300"
      height="300"
      aspect-ratio="1/1"
      cover
      class="rounded-circle"
    ></v-img>
  </div>
</template>

<style scoped></style>
