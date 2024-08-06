<script setup>
import { defineModel, defineProps } from "vue";

const model = defineModel();

const props = defineProps({
    data: String,
    placeholder: String,
});
</script>

<template>
    <div>
        {{ data }}
        <v-textarea
            v-model="model"
            variant="outlined"
            :placeholder="placeholder"
        ></v-textarea>
    </div>
</template>

<style scoped></style>
