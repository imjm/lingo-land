<script setup>
import { useTaleStore } from "@/stores/tales";
import { defineProps } from "vue";
const store = useTaleStore();
const props = defineProps({
  tale: {
    type: Object,
    required: true,
  },
});
const clickTale = () => {
  store.clickTale(props.tale);
};
</script>

<template>
  <!-- 약간 미완성입니다. 추가로 데이터 연결과 라우터 설정을 수정할 예정입니다. -->
  <v-sheet border="md" class="pa-6 mx-auto" max-width="800">
    <h4 class="text-h5 font-weight-bold mb-4">tale.title</h4>
    <v-img src="https://cdn.vuetifyjs.com/images/parallax/material.jpg"></v-img>

    <p class="mb-8">
      tale.description

      <br />
      <br />
    </p>

    <v-btn
      class="text-none text-black mb-4"
      color="red-accent-2"
      size="x-large"
      variant="flat"
      block
    >
      다음
    </v-btn>
  </v-sheet>
</template>
<style scoped></style>
