<script setup>
import GroupMemberList from "@/components/group/GroupMemberList.vue";
import { useGroupMemberStore } from "@/stores/groupMember";
import { useGroupStore } from "@/stores/groups";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const groupMemberStore = useGroupMemberStore();
const groupStore = useGroupStore();

const groupMemberList = ref();
const groupInfo = ref({
    name: "",
    id: "",
    description: "",
    memberCount: "",
    leaderNickname: "",
});

onMounted(async () => {
    const groupMemberPromise = groupMemberStore.getMyGroupMembers(
        route.params.groupId
    );

    groupMemberPromise.then(async (promise) => {
        groupMemberList.value = promise;
    });

    const groupPromise = groupStore.getGroup(route.params.groupId);

    groupPromise.then((promise) => {
        groupInfo.value = promise;
    });
});
</script>

<template>
    <br />
    <v-row>
        <v-col cols="2"> </v-col>
        <v-col cols="8">
            <v-sheet class="g-10">
                <v-row>
                    <v-col cols="5">
                        <h1 class="mx-5 my-0">{{ groupInfo.name }}</h1>
                    </v-col>
                    <v-col>
                        <v-autocomplete
                            append-inner-icon="mdi-microphone"
                            class="mx-5 mt-1"
                            density="comfortable"
                            menu-icon=""
                            placeholder="이름을 입력하세요."
                            prepend-inner-icon="mdi-magnify"
                            style="max-width: 350px"
                            theme="light"
                            variant="solo"
                            auto-select-first
                            item-props
                            rounded
                        ></v-autocomplete>
                    </v-col>
                </v-row>

                <GroupMemberList :groupMemberList="groupMemberList" />

                <v-row height="10"></v-row>
            </v-sheet>
        </v-col>
    </v-row>
</template>
