<script setup>
    import { defineProps } from 'vue';

    const props = defineProps({

    })
</script>

<template>
    <button @click="$emit('goGame')">게임버튼</button>
</template>

<style scoped>

</style>