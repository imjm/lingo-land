<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import router from "@/router";
import GroupMemberListItem from "./GroupMemberListItem.vue";

const props = defineProps({
    groupMemberList: Object,
});


</script>

<template>
    <v-expansion-panels class="pa-4" variant="popout">
        <v-expansion-panel
            v-for="(groupMember, i) in groupMemberList"
            :key="i"
            hide-actions
        >
            <GroupMemberListItem :groupMember="groupMember" />
        </v-expansion-panel>
    </v-expansion-panels>


</template>

<style scoped></style>
