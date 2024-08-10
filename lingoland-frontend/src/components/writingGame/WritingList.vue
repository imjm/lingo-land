<script setup>
import { onMounted } from 'vue';
import { defineProps } from 'vue';
const props = defineProps({
    story: String,
})
</script>

<template>
    <div class="image d-flex justify-center align-center">
        <div>{{ story }}</div>
    </div>
</template>

<style scoped>
.image {
    background-image: url("/balloon1.png");
    /* height: 200px; */
    /* width: 80%; */
    background-size: cover;
    background-position: center;

}
</style>
