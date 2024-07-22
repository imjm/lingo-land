<script setup>
import { useMemberStore } from "@/stores/members";
import { defineProps } from "vue";
const store = useMemberStore();
const props = defineProps({
  member: {
    type: Object,
    required: true,
  },
});
const clickMember = () => {
  store.clickMember(props.member);
};
</script>

<template>
  <v-expansion-panel-title>
    <v-row align="center" class="spacer" no-gutters>
      <v-col cols="4" md="1" sm="2">
        <v-avatar size="36px">
          <v-img
            v-if="member.avatar"
            alt="Avatar"
            src="https://avatars0.githubusercontent.com/u/9064066?v=4&s=460"
          ></v-img>
        </v-avatar>
      </v-col>
      <v-col class="hidden-xs-only text-left ms-2" md="3" sm="5">
        <strong v-html="member.name"></strong>
        (<strong v-html="member.rank"></strong>)
      </v-col>

      <v-col class="text-no-wrap text-left text-grey" sm="3">
        <strong v-html="member.description"></strong>
      </v-col>
    </v-row>
  </v-expansion-panel-title>
</template>

<style scoped></style>
