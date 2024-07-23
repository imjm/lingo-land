<script setup>
import { defineProps } from 'vue';

const props = defineProps({    
    data: String,
    placeholder: String
});

</script>

<template>
    <div>
        {{ data }}
        <v-textarea variant="outlined" :placeholder="placeholder"></v-textarea>
    </div>
</template>

<style scoped>

</style>