<script setup>
import ImageBox from "./ImageBox.vue";

const imageSource = "src\\assets\\sampleImg.jpg";
</script>

<template>
    <v-container>
        <v-card width="500">
            <v-row class="d-flex flex-column ma-6"e>
                <v-col class="d-flex align-center justify-center">
                    <ImageBox
                        aspectRatio="1/1"
                        :width="300"
                        :height="300"
                        :source="imageSource"
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
