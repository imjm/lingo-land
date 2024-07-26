<script setup>
import { defineProps } from "vue";

const props = defineProps({
    data: String,
});
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <div class="d-flex flex-column align-center justify-center mb-6">
            <div class="text-h4 mb-9 font-weight-bold">
                나만의 동화를 만들고 있어요...
            </div>
            <v-progress-circular
                :size="400"
                :width="40"
                color="green"
                indeterminate
            ></v-progress-circular>
        </div>
    </v-main>
</template>

<style scoped></style>
