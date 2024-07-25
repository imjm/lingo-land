<script setup>
import { useTaleStore } from "@/stores/tales";
import { defineProps } from "vue";
import DropBox from "../common/DropBox.vue";
const store = useTaleStore();
const props = defineProps({
    tale: {
        type: Object,
        required: true,
    },
});
const clickTale = () => {
    store.clickTale(props.tale);
};
</script>

<template>
    <v-col cols="12">
        <v-card variant="elevated">
            <div class="d-flex flex-no-wrap justify-space-between">
                <v-avatar class="ma-3" rounded="0" size="125">
                    <v-img :src="tale.image"></v-img>
                </v-avatar>
                <div>
                    <v-card-title class="text-h5">
                        {{ tale.title }}
                    </v-card-title>

                    <v-card-text>{{ tale.description }}</v-card-text>

                    <v-card-actions>
                        <v-btn
                            class="ms-2"
                            size="small"
                            text="소설 읽기"
                            variant="elevated"
                            color="brown"
                        ></v-btn>

                        <DropBox :item="['전체공개','나만보기']"/>
                    </v-card-actions>
                </div>
            </div>
        </v-card>
    </v-col>
    <br />
</template>

<style scoped></style>
