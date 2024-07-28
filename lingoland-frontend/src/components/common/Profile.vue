<script setup>
import ImageBox from "./ImageBox.vue";
import { defineProps } from "vue";

const props = defineProps({
    source: String,
});
</script>

<template>
    <v-card width="70%" height="100%">
        <v-row class="d-flex flex-column ma-6">
            <v-col class="d-flex align-center justify-center">
                <ImageBox :source="source" />
            </v-col>
            <v-col
                class="d-flex align-center justify-center text-h3 font-weight-bold my-3"
            >
                이름
            </v-col>
            <v-col
                class="d-flex align-center justify-center text-h4 font-weight-bold my-3"
            >
                신분
            </v-col>
            <v-col class="d-flex align-center justify-center text-h5 my-1">
                <v-progress-linear rounded height="25" color="primary" model-value="20">
                    <template v-slot:default="{ value }">
                        <div class="text-button">{{ Math.ceil(value) }}%</div>
                    </template>
                </v-progress-linear>
            </v-col>
        </v-row>
    </v-card>
</template>

<style scoped></style>
