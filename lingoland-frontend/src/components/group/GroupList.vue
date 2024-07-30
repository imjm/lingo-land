<script setup>
import GroupListItem from "./GroupListItem.vue";
import { useGroupStore } from "@/stores/groups";
import { storeToRefs } from "pinia";

const store = useGroupStore();

const { groups } = storeToRefs(store);
</script>

<template>
    <v-expansion-panels class="d-flex pa-4 game-member-list" variant="popout">
        <v-expansion-panel v-for="(group, i) in groups" :key="i" hide-actions>
            <GroupListItem
                :group="group"
                @click-event="
                    $router.push({
                        name: 'groupDetail',
                        params: { groupId: '1' },
                    })
                "
            />
        </v-expansion-panel>
    </v-expansion-panels>
</template>

<style scoped>
.game-member-list {
    max-height: 70%; /* 최대 높이 설정 */
    overflow-y: auto; /* 수직 스크롤을 가능하게 설정 */
}
</style>
