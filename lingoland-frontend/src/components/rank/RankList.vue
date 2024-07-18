<script setup>
import DropBox from '../common/DropBox.vue';
import RankListItem from './RankListItem.vue';
import { useRankStore } from '@/stores/ranks'

const store = useRankStore();
</script>

<template>
    <div>
        <h1>순위</h1>
        <DropBox/>
        <v-row align="center" justify="center" dense>
        <RankListItem

            />
            <!-- <RankListItem
            v-for="rank in store.ranks"
            :key="rank.id"
            :rank="rank"
            /> -->
    </v-row>

    </div>
</template>

<style scoped>

</style>