<script setup>
import ImageBox from "./ImageBox.vue";

const props = defineProps({
    height: Number,
    width: Number,
    source: String,
});


</script>

<template>
    <v-container>
        <v-card width="500">
            <v-row class="d-flex flex-column ma-6"e>
                <v-col class="d-flex align-center justify-center">
                    <ImageBox
                        :width="300"
                        :height="300"
                        :source="source"
                    />
                </v-col>
                <v-col class="d-flex align-center justify-center"> 이름 </v-col>
                <v-col class="d-flex align-center justify-center"> 신분 </v-col>
                <v-col class="d-flex align-center justify-center"> 경험치 바 </v-col>
            </v-row>
        </v-card>
    </v-container>
</template>

<style scoped></style>
