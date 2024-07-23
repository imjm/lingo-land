<script setup>
import { defineProps } from "vue";
/*
 id: 컴포넌트 id
 type: input 컴포넌트 type 지정
 data: input 컴포넌트 위에 지정할 이름 
*/
const props = defineProps({
    id: String,
    type: String,
    data: String,
});
</script>

<template>
    <div>
        <v-text-field :type="type" :id="id"></v-text-field>
    </div>
</template>

<style scoped></style>
