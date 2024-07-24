<script setup>
import GroupListItem from "./GroupListItem.vue";
import { useGroupStore } from "@/stores/groups";

const store = useGroupStore();
</script>

<template>
    <v-expansion-panels class="pa-4" variant="popout">
        <v-expansion-panel
            v-for="(group, i) in store.groups"
            :key="i"
            hide-actions
        >
            <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
            <GroupListItem :group="group" />
        </v-expansion-panel>
    </v-expansion-panels>
</template>

<style scoped></style>
