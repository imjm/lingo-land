<script setup>
/*
 id: 컴포넌트 id
 type: button 컴포넌트 type 지정
 data: button 컴포넌트에 지정할 이름 
*/
const props = defineProps({
    aspectRatio: String,
    width: Number,
    source: String,
});
</script>

<template>
    <div>
        <v-img
            :width=width
            :aspect-ratio="aspectRatio"
            cover
            :src="source"
        ></v-img>
    </div>
</template>

<style scoped></style>
