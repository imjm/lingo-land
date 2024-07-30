<script setup>
import { useUserStore } from "@/stores/user";
import { ref, defineProps, onMounted } from "vue";
import ImageBox from "./ImageBox.vue";

const props = defineProps({
    source: String,
});

const userStore = useUserStore();

const userProfile = ref({
    nickname: "",
    profileImage: null,
    experiencePoint: 0,
});

onMounted(() => {
    const profile = userStore.getProfile();

    profile.then((getValue) => {
        userProfile.value.nickname = getValue.nickname;
        userProfile.value.experiencePoint = getValue.experiencePoint;

        if (getValue.profileImage === null) {
            userProfile.value.profileImage = props.source;
        }
    });
});
</script>

<template>
    <v-card width="90%" height="100%" class="d-flex align-center">
        <v-row class="d-flex flex-column ma-6">
            <v-col class="d-flex align-center justify-center">
                <ImageBox :source="userProfile.profileImage" />
            </v-col>
            <v-col
                class="d-flex align-center justify-center text-h3 font-weight-bold my-3"
            >
                {{ userProfile.nickname }}
            </v-col>
            <v-col
                class="d-flex align-center justify-center text-h4 font-weight-bold my-3"
            >
                신분
            </v-col>
            <v-col class="d-flex align-center justify-center text-h5 my-1">
                <v-progress-linear
                    rounded
                    height="25"
                    color="primary"
                    :model-value="userProfile.experiencePoint"
                >
                    <template v-slot:default="{ value }">
                        <div class="text-button">{{ Math.ceil(value) }}%</div>
                    </template>
                </v-progress-linear>
            </v-col>
        </v-row>
    </v-card>
</template>

<style scoped></style>
