<script setup>
import TaleListItem from "./TaleListItem.vue";
import { useTaleStore } from "@/stores/tales";

const store = useTaleStore();
</script>

<template>
    <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
    <TaleListItem v-for="(tale, i) in store.tales" :key="i" :tale="tale" />
</template>

<style scoped></style>
