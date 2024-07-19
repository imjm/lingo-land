<script setup>
import GroupMemberListItem from "./GroupMemberListItem.vue";
import { useProfileStore } from "@/stores/profiles";

const store = useProfileStore();
</script>

<template>
  <div>
    <h1>group.name</h1>

    <v-expansion-panels class="pa-4" variant="popout">
      <GroupMemberListItem />

      <!-- 데이터가 없어 v-for문을 돌렸을 경우 예시가 보이지 않아 임의로 작성 후에 추가로 코드 수정 필요 groups store 코드에서 데이터 받아오는 코드도 수정 필요
        -->
      <!-- <GroupMemberListItem
            v-for="group in store.groups"
            :key="group.id"
            :group="group"
            /> -->
    </v-expansion-panels>
  </div>
</template>

<style scoped></style>
