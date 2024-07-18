<script setup>
/*
 id: 컴포넌트 id
 type: button 컴포넌트 type 지정
 data: button 컴포넌트에 지정할 이름 
*/
const props = defineProps({
    id: String,
    type: String,
    data: String,
});
</script>

<template>
    <div>
        {{ data }}
    </div>
</template>

<style scoped>

</style>