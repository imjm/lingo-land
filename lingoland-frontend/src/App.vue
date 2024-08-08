<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <!-- <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
  <RouterLink :to="{ name: 'gameRoom', params: { roomId: '1' } }">
    gameRoom</RouterLink
  >
  | <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |  -->

  <RouterLink :to="{ name: 'writingGame', params: { roomId: '1' } }">
    writingGame</RouterLink
  >
  |
  <RouterLink :to="{ name: 'writingGameResult', params: { roomId: '1' } }">
    writingGameResult</RouterLink
  >
<!--   
  |
  <RouterLink :to="{ name: 'runningGame', params: { roomId: '1' } }">
    runningGame</RouterLink
  >
  |
  <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
  <RouterLink :to="{ name: 'myPage' }">myPage</RouterLink> |
  <RouterLink :to="{ name: 'myPageModify' }">mypageModify</RouterLink> |

  <RouterLink :to="{ name: 'groupList' }">groupList</RouterLink> |
  
  <RouterLink
    :to="{ name: 'runningGameResult', params: { roomId: '1' } }"
    >runningGameResult</RouterLink
  >
  | -->

  <v-app class="bg-amber-lighten-3 gamja-flower-regular">
    <RouterView></RouterView>
  </v-app>
</template>

<style>



</style>
