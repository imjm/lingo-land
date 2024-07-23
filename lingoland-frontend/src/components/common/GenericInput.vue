<script setup>
import { defineProps } from "vue";
/*
 id: 컴포넌트 id
 type: input 컴포넌트 type 지정
 data: input 컴포넌트 위에 지정할 이름 
 variant: input field 모양을 지정
*/
const props = defineProps({
    id: String,
    type: String,
    data: String,
    variant: String,
});
</script>

<template>
    <div>
        {{ data }}
        <v-text-field :type="type" :id="id" variant="outlined"></v-text-field>
    </div>
</template>

<style scoped></style>
