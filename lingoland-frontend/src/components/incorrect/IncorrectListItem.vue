<script setup>
import { defineProps } from "vue";
import GenericButton from "../common/GenericButton.vue";

const props = defineProps({
  incorrect: Object,
});
</script>

<template>
  <v-list-item-content>
    <v-container class="border-md">
      <v-row rows="2">
        <div>{{ incorrect }}</div>
      </v-row>
      <v-row rows="10">
        <v-col cols="4">
          <img
            src="/src/assets/sampleImg.jpg"
            class="rounded-circle mx-auto"
            style="height: 64px; width: 64px"
          />
        </v-col>
        <v-col cols="8">
          <div>incorrect.question </div>
          <div>incorrect.answer </div>
          <div class="d-flex justify-end">

            <GenericButton :style="{ backgroundColor : '#4C4637', color : 'white'}" data="학습완료"/>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-list-item-content>
</template>

<style scoped>
.border-md {
  background-color: #fff1c2;
}
</style>
