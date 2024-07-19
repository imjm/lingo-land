<script setup>
import DropBox from "../common/DropBox.vue";
import RankListItem from "./RankListItem.vue";
import { useRankStore } from "@/stores/ranks";

const store = useRankStore();
</script>

<template>
  <div>
    <h1>순위</h1>
    <DropBox />
    <v-row align="center" justify="center" dense>
      <RankListItem />
      <!-- 데이터가 없어 v-for문을 돌렸을 경우 예시가 보이지 않아 임의로 작성 후에 추가로 코드 수정 필요 ranks store 코드에서 데이터 받아오는 코드도 수정 필요
        -->
      <!-- <RankListItem
            v-for="rank in store.ranks"
            :key="rank.id"
            :rank="rank"
            /> -->
    </v-row>
  </div>
</template>

<style scoped></style>
