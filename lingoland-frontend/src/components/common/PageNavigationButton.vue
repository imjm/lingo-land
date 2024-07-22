<script setup>
/*
 id: 컴포넌트 id
 type: button 컴포넌트 type 지정
 data: button 컴포넌트에 지정할 이름 
*/
const props = defineProps({
  type: String,
  data: String,
  backgroundColor : String,
});
</script>

<template>
  <div>
    <v-btn class="mb-5" :style="{ width: '90vw', height: '300px', backgroundColor: backgroundColor, fontSize : 'x-large'}">
      {{ data }}
      
    </v-btn>
  </div>
</template>

<style scoped>

</style>
