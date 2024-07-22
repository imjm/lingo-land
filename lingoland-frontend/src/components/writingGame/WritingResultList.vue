<script setup>
import WritingResultListItem from "./WritingResultListItem.vue";
</script>

<template>
    <v-carousel hide-delimiter-background progress="primary" hide-delimiters>
        <WritingResultListItem
            source="\src\assets\달리기.jpg"
            text="어쩌구 저쩌구"
        />
        <WritingResultListItem
            source="\src\assets\sampleImg.jpg"
            text="어쩌구 저쩌구"
        />
    </v-carousel>
</template>

<style scoped></style>
