<script setup>
import { defineProps, onMounted } from "vue";
import sampleImage from "@/assets/sampleImg.jpg";
import router from "@/router";

const props = defineProps({
    groupMember: {
        type: Object,
        required: true,
    },
    groupLeader: String,
});

onMounted(() => {
    if (props.groupMember.profileImage === null) {
        props.groupMember.profileImage = sampleImage;
    }
});

function memberDetail() {
    router.push({
        name: "groupMemberDetail",
        params: { memberId: props.groupMember.loginId },
    });
}
</script>

<template>
    <v-expansion-panel-title @click="memberDetail">
        <v-row class="spacer" no-gutters>
            <v-col cols="4" md="1" sm="2">
                <v-avatar size="36px">
                    <v-img :src="groupMember.profileImage"></v-img>
                </v-avatar>
            </v-col>
            <v-col cols="4" class="hidden-xs-only text-left ms-2" md="3" sm="5">
                <strong>{{ groupMember.nickname }}</strong>
            </v-col>

            <v-col cols="4" class="text-no-wrap text-left text-grey" sm="3">
                <strong>{{ groupMember.description }}</strong>
            </v-col>

            <v-spacer></v-spacer>
            <v-col
                v-if="groupMember.nickname === groupLeader"
                class="text-no-wrap text-right text-grey"
                sm="auto"
            >
                <span class="material-symbols-outlined" style="color: #d1b041">
                    verified
                </span>
            </v-col>
        </v-row>
    </v-expansion-panel-title>

    <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
</template>

<style scoped></style>
