<script setup>
import { defineEmits, defineProps } from "vue";

const props = defineProps({
    group: {
        type: Object,
        required: true,
    },
});

defineEmits(["clickEvent"]);
</script>

<template>
    <v-expansion-panel-title @click="$emit('clickEvent')">
        <v-row class="spacer" no-gutters>
            <v-col class="text-left ms-2" md="3" sm="5">
                <div class="h2 text-grey font-weight-bold">
                    {{ group.name }}
                </div>
            </v-col>

            <v-col class="text-no-wrap text-left text-grey" sm="3">
                <div class="h2 text-grey font-weight-bold">
                    {{ group.description }}
                </div>
            </v-col>
        </v-row>
    </v-expansion-panel-title>
</template>

<style scoped></style>
