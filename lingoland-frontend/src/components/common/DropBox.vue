<script setup>
/*
 id: 컴포넌트 id
 label: dropbox 라벨 이름
 item: dropbox 컴포넌트에서 사용할 세부 아이템 리스트
*/
const props = defineProps({
    id: String,
    label: String,
    item: Array,
    width: Number,
});
</script>

<template>
    <v-select
        clearable
        variant="outlined"
        :width="width"
        color="black"
        :items="[
            'California',
            'Colorado',
            'Florida',
            'Georgia',
            'Texas',
            'Wyoming',
        ]"
    ></v-select>
</template>

<style scoped></style>
