<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import router from "@/router";
import GroupMemberListItem from "./GroupMemberListItem.vue";

const props = defineProps({
    groupMemberList: Object,
});

function modify() {
    // 그룹장인 경우만 수정되도록
    console.log("modify");
    router.push({ name: "groupModify" });
}
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

    <v-row>
        <v-col>
            <div class="d-flex justify-end ma-2">
                <GenericButton
                    class="mx-5"
                    data="그룹 수정"
                    @click-event="modify"
                />
            </div>
        </v-col>
    </v-row>
</template>

<style scoped></style>
