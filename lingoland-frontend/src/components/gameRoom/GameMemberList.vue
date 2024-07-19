<template>
  <v-list class="game-member-list">
    <v-list-item v-for="(member, index) in members" :key="index">
      <GameMemberListItem :member="member"></GameMemberListItem>
    </v-list-item>
  </v-list>
</template>

<script setup>
import { defineProps } from "vue";
import GameMemberListItem from "./GameMemberListItem.vue";

const props = defineProps({
  members: Array,
});
</script>

<style scoped>
.game-member-list {
  max-height: 70%; /* 최대 높이 설정 */
  overflow-y: auto; /* 수직 스크롤을 가능하게 설정 */
}
</style>
