<template>
    <v-expansion-panel-title @click="clickGroup">
      <v-row align="center" class="spacer" no-gutters>
        <v-col class="hidden-xs-only text-left ms-2" md="3" sm="5">
          <strong v-html="group.name"></strong>
          <span v-if="group.total" class="text-grey">
            &nbsp;({{ group.total }})
          </span>
        </v-col>
        <v-col class="text-no-wrap text-left text-grey" sm="3">
          <strong v-html="group.description"></strong>
        </v-col>
      </v-row>
    </v-expansion-panel-title>
  </template>
  
  <script setup>
  import { useGroupStore } from "@/stores/groups";
  import { defineProps } from "vue";
  
  const store = useGroupStore();
  const props = defineProps({
    group: {
      type: Object,
      required: true,
    },
  });
  
  const clickGroup = () => {
    store.clickGroup(props.group);
  };
  </script>
  
  <style scoped></style>
  