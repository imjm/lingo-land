<script setup>
import { RouterView } from "vue-router";
import TaleDetailView from "@/views/TalesDetailView.vue"
</script>

<template>
    <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
    <RouterLink :to="{ name: 'gameRoom' }"> gameRoom</RouterLink> |
    <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
    <RouterLink :to="{ name: 'writingGame' }"> writingGame</RouterLink> |
    <RouterLink :to="{ name: 'writingGameResult' }">
    <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
    <RouterLink :to="{ name: 'groups' }">groupjoin</RouterLink>|
    <RouterLink :to="{ name: 'groupId' }">groupId</RouterLink>|
        writingGameResult</RouterLink
    >
    |
    <v-app class="bg-amber-lighten-3">
        <RouterView></RouterView>
    </v-app>
</template>

<style scoped></style>
