<script setup>
import DropBox from "../common/DropBox.vue";
import RankListItem from "./RankListItem.vue";
import { useMemberStore } from "@/stores/members";

const store = useMemberStore();
</script>

<template>
    <div>
        <h1>순위</h1>
        <DropBox />
        <v-expansion-panels class="pa-4" variant="popout">
            <v-expansion-panel
                v-for="(member, i) in store.members"
                :key="i"
                hide-actions
            >
                <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
                <RankListItem :member="member" />
            </v-expansion-panel>
        </v-expansion-panels>
    </div>
</template>

<style scoped></style>
