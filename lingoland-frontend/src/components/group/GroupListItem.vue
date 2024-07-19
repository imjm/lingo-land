<script setup>
import { useGroupStore } from "@/stores/groups";
import { defineProps } from "vue";
const store = useGroupStore();
const props = defineProps({
  group: {
    type: Object,
    required: true,
  },
});
const clickGroup = () => {
  store.clickGroup(props.group);
};
</script>

<template>
  <v-col cols="12" md="6" @click="clickGroup()">
    <v-card class="mx-auto" subtitle="group.description" title="group.name">
<!-- 해당파트는 원래 v-bind로 연결해야 하지만 group data가 없어 자료가 뜨지 않아 그냥 작성함 -->

    </v-card>
  </v-col>
</template>

<style scoped></style>
