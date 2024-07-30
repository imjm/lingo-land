<script setup>
import { useGroupStore } from "@/stores/groups";
import { onMounted, ref, defineProps } from "vue";
import GroupListItem from "./GroupListItem.vue";

const props = defineProps({
    checkMyGroup: Boolean,
});

const store = useGroupStore();

const groupList = ref();

onMounted(() => {
    let groupListPromise;

    if (props.checkMyGroup) {
        groupListPromise = store.getMyGroups();
    } else {
        groupListPromise = store.getGroups();
    }

    groupListPromise.then((promiseValue) => {
        groupList.value = promiseValue;
    });
});
</script>

<template>
    <v-expansion-panels class="d-flex pa-4 game-member-list" variant="popout">
        <v-expansion-panel
            v-for="(group, i) in groupList"
            :key="i"
            hide-actions
        >
            <GroupListItem
                :group="group"
                @click-event="
                    $router.push({
                        name: 'groupDetail',
                        params: { groupId: group.id },
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
