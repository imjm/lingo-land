<template>
    <v-expansion-panels class="pa-4" variant="popout">
      <v-expansion-panel
        v-for="(group, i) in store.groups"
        :key="i"
        hide-actions
      >
        <GroupListItem :group="group" />
      </v-expansion-panel>
    </v-expansion-panels>
  </template>
  
  <script setup>
  import GroupListItem from "./GroupListItem.vue";
  import { useGroupStore } from "@/stores/groups";
  import { onMounted } from 'vue';
  
  // Store 불러오기
  const store = useGroupStore();
  
  // 컴포넌트가 마운트될 때 데이터 가져오기
  onMounted(() => {
    store.getGroups();
  });
  </script>
  
  <style scoped></style>
  