<script setup>
import { useGroupMemberStore } from "@/stores/groupMember";
import DropBox from "../common/DropBox.vue";
import RankListItem from "./RankListItem.vue";

const groupMemberStore = useGroupMemberStore();
</script>

<template>
    <div>
        <v-card color="rgba(172, 204, 124, 1)" class="pt-3" height="100%">
            <v-row>
                <v-col>
                    <h1 class="ml-10">순위</h1>
                </v-col>
                <v-col class="d-flex justify-end">
                    <DropBox width="50%" class="pr-8" />
                </v-col>
            </v-row>
            <v-expansion-panels class="pa-4" variant="popout">
                <v-expansion-panel
                    v-for="(member, i) in groupMemberStore.members"
                    :key="i"
                    hide-actions
                >
                    <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
                    <RankListItem :member="member" />
                </v-expansion-panel>
            </v-expansion-panels>
        </v-card>
    </div>
</template>

<style scoped></style>
