<template>
    <v-card width="400">
      <CountdownTimer />
      <v-textarea font-size="1.2em" variant="outlined"> </v-textarea>
      <div class="d-flex justify-end">
        <SubmitButton width="100" data="제출" />
      </div>
    </v-card>
  </template>
  
  <script setup>
  import CountdownTimer from './CountdownTimer.vue';
  import SubmitButton from '../common/SubmitButton.vue';
  </script>
  
  <style scoped>
  /* 필요한 스타일 추가 */
  </style>
  