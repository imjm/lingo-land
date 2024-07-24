<script setup>
import { RouterView } from "vue-router";
</script>

<template>
    <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
    <RouterLink :to="{ name: 'gameRoom', params: { roomId: '1' } }">
        gameRoom</RouterLink
    >
    | <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
    <RouterLink :to="{ name: 'writingGame', params: { roomId: '1' } }">
        writingGame</RouterLink
    >
    |
    <RouterLink :to="{ name: 'writingGameResult', params: { roomId: '1' } }">
        writingGameResult</RouterLink
    >
    |
    <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
    <RouterLink :to="{ name: 'myPage' }">myPage</RouterLink> |
    <RouterLink :to="{ name: 'myPageModify' }">mypageModify</RouterLink> |
    <RouterLink :to="{ name: 'bookList' }">bookList</RouterLink> |
    <RouterLink :to="{ name: 'bookDetail', params: { bookId: '1' } }"
        >bookListDetail</RouterLink
    >
    |
    <RouterLink :to="{ name: 'groupList' }">groupList</RouterLink> |
    <RouterLink :to="{ name: 'groupDetail', params: { groupId: '1' } }">groupId</RouterLink> | |
    <RouterLink :to="{ name: 'groupCreate' }">groupCreate</RouterLink> |
    <RouterLink :to="{ name: 'groupModify' }">groupModify</RouterLink> |

    <v-app class="bg-amber-lighten-3">
        <RouterView></RouterView>
    </v-app>
</template>

<style scoped></style>
