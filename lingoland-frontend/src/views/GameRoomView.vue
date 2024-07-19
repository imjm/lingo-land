<script setup>

import { ref } from "vue";
import GameMemberList from "@/components/gameRoom/GameMemberList.vue";
import GameButton from "@/components/gameRoom/GameButton.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";


const memberList = ref([
  '멤버 1',
  '멤버 2',
  '멤버 3',
  '멤버 4',
  '멤버 5',
  '멤버 1',
  '멤버 2',
  '멤버 3',
  '멤버 4',
  '멤버 5',
  // 데이터 받아오기
]);

</script>

<template>
  <v-container>
    <h1>GameRoomView</h1>
    <v-row>
      <v-col cols="4">
        <GameMemberList :members="memberList" />
      </v-col>
      <v-col cols="8">
        <GameButton></GameButton>
        <GameButton></GameButton>
        <PageNavigationButton data="QR 생성"></PageNavigationButton>
        <span>방 코드 : 어쩌구 저쩌구</span>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>


</style>
