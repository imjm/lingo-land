<script setup>
import { defineProps } from "vue";

const props = defineProps({
    member: Object,
});
</script>

<template>
    <v-list-item-content>
        <v-container class="border-md">
            <v-row rows="2">
                <div>{{ member }}</div>
            </v-row>
            <v-row rows="10">
                <v-col cols="4">
                    <img
                        src="/src/assets/sampleImg.jpg"
                        class="rounded-circle mx-auto"
                        style="height: 64px; width: 64px"
                    />
                </v-col>
                <v-col cols="8">
                    <div>member.신분</div>
                    <div>member.자기소개</div>
                </v-col>
            </v-row>
        </v-container>
    </v-list-item-content>
</template>

<style scoped>
.border-md {
    background-color: #fff1c2;
}
</style>
