<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import router from "@/router";
import { useMemberStore } from "@/stores/members";
import { ref } from "vue";
import GroupMemberListItem from "./GroupMemberListItem.vue";
const store = useMemberStore();
const dialog = ref(false);

function modify() {
    console.log("modify");
    router.push({ name: "groupModify" });
}
</script>

<template>
    <v-expansion-panels class="pa-4" variant="popout">
        <v-expansion-panel
            v-for="(member, i) in store.members"
            :key="i"
            hide-actions
        >
            <GroupMemberListItem :member="member" />
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
