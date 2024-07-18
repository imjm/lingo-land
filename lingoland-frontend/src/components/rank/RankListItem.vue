<script setup>
import { useRankStore } from '@/stores/ranks'
import { defineProps } from 'vue';
const store = useRankStore();
const props = defineProps({
    rank:{
        type: Object,
        required:true,
    }
})
const clickRank = () => {
    store.clickRank(props.rank);
}

</script>

<template>


  

  

  
      <v-col cols="12" md="6" @click="clickRank()">
        <v-card
          class="mx-auto"
          subtitle="prepend and append"
          title="Avatars"
        >
          <template v-slot:prepend>
            <v-avatar color="blue-darken-2">
              <v-icon icon="mdi-alarm"></v-icon>
            </v-avatar>
          </template>
          <template v-slot:append>
            <v-avatar size="24">
              <v-img
                alt="John"
                src="https://cdn.vuetifyjs.com/images/john.png"
              ></v-img>
            </v-avatar>
          </template>
          <v-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.</v-card-text>
        </v-card>
      </v-col>
  </template>

<style scoped>

</style>