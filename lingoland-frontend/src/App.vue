<script setup>
import { RouterView } from "vue-router";
</script>

<template>
    <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
    <RouterLink :to="{ name: 'gameRoom' }"> gameRoom</RouterLink> |
    <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
    <RouterLink :to="{ name: 'writingGame' }"> writingGame</RouterLink> |
    <RouterLink :to="{ name: 'writingGameResult' }">
        writingGameResult</RouterLink
    >
    |
    <v-app class="bg-amber-lighten-3">
        <RouterView></RouterView>
    </v-app>
</template>

<style scoped></style>
