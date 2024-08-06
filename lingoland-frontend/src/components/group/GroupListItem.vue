<script setup>
import { defineEmits, defineProps } from "vue";

const props = defineProps({
    group: {
        type: Object,
        required: true,
    },
    groupLeader: String,
});

defineEmits(["clickEvent"]);
</script>

<template>
    <v-expansion-panel-title @click="$emit('clickEvent')">
        <v-row class="spacer" no-gutters>
            <v-col class="text-left ms-2" md="3" sm="5">
                <div class="h2 font-weight-bold">
                    {{ group.name }}
                </div>
            </v-col>

            <v-col class="text-no-wrap text-left" sm="3">
                <div class="h2 font-weight-bold">
                    {{ group.description }}
                </div>
            </v-col>

            <v-col class="text-right text-grey ms-2" sm="3">
                <div class="h2 text-grey font-weight-bold">
                    그룹원 {{ group.memberCount }} 명
                </div>
            </v-col>
        </v-row>
    </v-expansion-panel-title>
</template>

<style scoped></style>
