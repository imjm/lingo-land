<script setup>
import { defineProps } from "vue";
/*
 id: 컴포넌트 id
 type: button 컴포넌트 type 지정
 data: button 컴포넌트에 지정할 이름 
*/
const props = defineProps({
    type: String,
    data: String,
    backgroundColor: String,
    source: String,
});
</script>

<template>
    <div>
        <v-btn
            class="mb-5"
            :style="{
                width: '90vw',
                height: '300px',
                backgroundColor: backgroundColor,
                // fontSize: 'x-large',
            }"
            @click="$emit('clickEvent')"
        >
            <div class="d-flex flex-column">
                <div class="text-h4 font-weight-black">
                    {{ data }}
                </div>
                <div class="d-flex justify-center align-center pa-1" style="flex: 1">
                    <img
                        v-if="source"
                        :src="source"
                        style="
                            max-width: 100%;
                            height: 90%;
                            object-fit: contain;
                        "
                        alt="Icon"
                    />
                </div>
            </div>
        </v-btn>
    </div>
</template>

<style scoped></style>
