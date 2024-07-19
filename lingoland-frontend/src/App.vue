<script setup></script>

<template>
  <div id="app">
    <v-container>
      <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
      <RouterLink :to="{ name: 'gameRoom' }"> gameRoom</RouterLink> |
      <RouterLink :to="{ name: 'joinRoom' }"> joinRoom</RouterLink> |
      <RouterView />
    </v-container>
  </div>
</template>

<style scoped></style>
