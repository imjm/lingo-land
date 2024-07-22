<script setup>
import Login from "@/components/login/Login.vue";
import GameSlide from "@/components/login/GameSlide.vue";
</script>

<template>
    <v-main class="d-flex align-center justify-center">
      <v-card width="1200" height="600">
        <div class="d-flex justify-space-between">
          <Login></Login>
          <GameSlide></GameSlide>
        </div>
      </v-card>
    </v-main>
</template>

<style scoped></style>
