<script setup>
import { defineProps } from "vue";
import GenericButton from "../common/GenericButton.vue";

const props = defineProps({
    incorrect: Object,
});
</script>

<template>
    <v-list-item-content>
        <v-container class="border-md">
            <v-row class="d-flex flex-column">
                <v-col>
                    <div class="text-h4">{{ incorrect.problem }}</div>
                </v-col>
                <v-col>
                    <div class="text-h6">
                        문제 해설: {{ incorrect.explanation }}
                    </div>
                </v-col>
                <v-col>
                    <div class="text-h5">1</div>
                </v-col>
                <v-col>
                    <div class="text-h5">2</div>
                </v-col>
                <v-col>
                    <div class="text-h5">3</div>
                </v-col>
                <v-col>
                    <div class="d-flex justify-end">
                        <GenericButton
                            :style="{
                                backgroundColor: '#4C4637',
                                color: 'white',
                            }"
                            data="학습완료"
                        />
                    </div>
                </v-col>
            </v-row>
        </v-container>
    </v-list-item-content>
</template>

<style scoped>
.border-md {
    background-color: #fff1c2;
    border-radius: 8px;
    margin: 8px 0px;
}
</style>
