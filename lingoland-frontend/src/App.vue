<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  
  <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
  <RouterLink :to="{ name: 'gameRoom' }"> gameRoom</RouterLink> |
  <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
  <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
  <RouterLink :to="{ name: 'groups' }">groupjoin</RouterLink>|
  <RouterLink :to="{ name: 'groupId' }">groupId</RouterLink>|
  <v-app class="bg-amber-lighten-3 d-flex align-center justify-center">
  <RouterView></RouterView>
    
  </v-app>
</template>

<style scoped></style>
