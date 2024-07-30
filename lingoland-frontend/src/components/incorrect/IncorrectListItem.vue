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
            <v-row rows="2">
                <div>{{ incorrect }}</div>
            </v-row>
            <v-row rows="10">
                <v-col>
                    <div class="text-h4">incorrect.question</div>
                    <div class="text-h5">incorrect.answer</div>
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
    border-radius : 8px;
    margin : 8px 0px;
}
</style>
