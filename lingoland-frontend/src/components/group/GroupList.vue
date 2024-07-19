<script setup>
import GroupListItem from "./GroupListItem.vue";
import { useGroupStore } from "@/stores/groups";

const store = useGroupStore();
</script>

<template>
  <div>
    <h1>내가 속한 그룹</h1>
    
    <v-row align="center" justify="center" dense>
      <GroupListItem />
      <!-- 데이터가 없어 v-for문을 돌렸을 경우 예시가 보이지 않아 임의로 작성 후에 추가로 코드 수정 필요 groups store 코드에서 데이터 받아오는 코드도 수정 필요
        -->
      <!-- <GroupListItem
            v-for="group in store.groups"
            :key="group.id"
            :group="group"
            /> -->
    </v-row>
  </div>
</template>

<style scoped></style>
