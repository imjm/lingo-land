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
    | <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
    <RouterLink :to="{ name: 'groups' }">groupjoin</RouterLink> |
    <RouterLink :to="{ name: 'groupId' }">groupId</RouterLink> |
    <RouterLink :to="{ name: 'myPage' }">mypage</RouterLink> |
    <RouterLink :to="{ name: 'myPageModify' }">mypageModify</RouterLink> |

    <v-app class="bg-amber-lighten-3">
        <RouterView></RouterView>
    </v-app>
</template>

<style scoped></style>
