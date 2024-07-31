<script setup>
import { defineProps, onMounted } from "vue";
import sampleImage from "@/assets/sampleImg.jpg";

const props = defineProps({
    groupMember: {
        type: Object,
        required: true,
    },
});

onMounted(() => {
    if (props.groupMember.profileImage === null) {
        props.groupMember.profileImage = sampleImage;
    }
});
</script>

<template>
    <v-expansion-panel-title>
        <v-row class="spacer" no-gutters>
            <v-col cols="4" md="1" sm="2">
                <v-avatar size="36px">
                    <v-img :src="groupMember.profileImage"></v-img>
                </v-avatar>
            </v-col>
            <v-col class="hidden-xs-only text-left ms-2" md="3" sm="5">
                <strong>{{ groupMember.nickname }}</strong>
            </v-col>

            <v-col class="text-no-wrap text-left text-grey" sm="3">
                <strong>{{ groupMember.description }}</strong>
            </v-col>
        </v-row>
    </v-expansion-panel-title>
</template>

<style scoped></style>
