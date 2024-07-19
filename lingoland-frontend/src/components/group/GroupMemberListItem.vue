<script setup>
import { useProfileStore } from "@/stores/profiles";
import { defineProps } from "vue";
const store = useProfileStore();
const props = defineProps({
  profile: {
    type: Object,
    required: true,
  },
});
const clickProfile = () => {
  store.clickProfile(props.profile);
};
</script>

<template>
  <v-col cols="12" md="6" @click="clickProfile()">
    <v-card class="mx-auto" subtitle="profile.description" title="profile.name">
<!-- 해당파트는 원래 v-bind로 연결해야 하지만 group data가 없어 자료가 뜨지 않아 그냥 작성함 -->

    </v-card>
  </v-col>
</template>

<style scoped></style>
