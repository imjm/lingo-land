<script setup>
import SubmitButton from "../common/SubmitButton.vue";
</script>

<template>
    <v-card width="400">
        <div class="text-h6">몇 초 남았을까요?</div>
        <v-textarea font-size="1.2em" variant="outlined"> </v-textarea>
        <div class="d-flex justify-end">
            <SubmitButton width="100" data="제출" />
        </div>
    </v-card>
</template>

<style scoped></style>
