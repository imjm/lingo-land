<script setup>
import ImageBox from "./ImageBox.vue";

const props = defineProps({
    source: String,
});
</script>

<template>
    <v-card width="500">
        <v-row class="d-flex flex-column ma-6">
            <v-col class="d-flex align-center justify-center">
                <ImageBox :source="source" />
            </v-col>
            <v-col class="d-flex align-center justify-center"> 이름 </v-col>
            <v-col class="d-flex align-center justify-center"> 신분 </v-col>
            <v-col class="d-flex align-center justify-center">
                경험치 바
            </v-col>
        </v-row>
    </v-card>
</template>

<style scoped></style>
