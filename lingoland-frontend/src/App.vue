<script setup>
import { RouterView } from "vue-router";
import TaleDetailView from "@/views/TalesDetailView.vue"
</script>

<template>
  <TaleDetailView/>
  <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
  <RouterLink :to="{ name: 'gameRoom' }"> gameRoom</RouterLink> |
  <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
  <RouterLink :to="{ name: 'groups' }">groupjoin</RouterLink>|
  <RouterLink :to="{ name: 'groupId' }">groupId</RouterLink>|
  <v-app class="bg-amber-lighten-3">
    <RouterView></RouterView>
  </v-app>
</template>

<style scoped></style>
