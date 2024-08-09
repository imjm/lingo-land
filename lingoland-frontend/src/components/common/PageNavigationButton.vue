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
    height : String,
});
</script>

<template>
    <div>
        <v-btn
            :style="{
                width: '90vw',
                backgroundColor: backgroundColor,
            }"
            :height="height"
            @click="$emit('clickEvent')"
        >
            <div class="d-flex flex-column">
                <div style="font-size:xx-large; font-weight: 700;"> 
                    {{ data }}
                </div>
                <div class="d-flex justify-center align-center pa-1" style="flex: 1">
                    <img
                        v-if="source"
                        :src="source"
                        style="
                            max-width: 100%;
                            height: 150px;
                            max-height:90%;
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
