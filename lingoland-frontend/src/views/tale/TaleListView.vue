<script setup>
import { ref, onMounted } from "vue";
import BackButton from "@/components/common/BackButton.vue";
import TaleList from "@/components/tale/TaleList.vue";


</script>

<template>
    <div class="main">
        <BackButton />
        <TaleList class="my-3" />
    </div>
</template>

<style scoped>
.main {
    height: 100vh;
    width: 100vw;
    background-color: white;
}
</style>
