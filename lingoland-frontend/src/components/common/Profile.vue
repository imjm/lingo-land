<script setup>
import sampleImage from "@/assets/sampleImg.jpg";
import { useUserStore } from "@/stores/user";
import { onMounted, ref, defineProps } from "vue";
import ImageBox from "./ImageBox.vue";
import { useRoute, useRouter } from "vue-router";

const userStore = useUserStore();

const router = useRouter();
const route = useRoute();

const props = defineProps({
    others: Boolean,
    id: String,
});
const userProfile = ref({
    nickname: "",
    profileImage: null,
    experiencePoint: 0,
    rank: "",
});

function selectItem(event) {
    console.log(event);

    if (event === "로그아웃") {
        userStore.logout();
    } else if (event === "내 정보 수정") {
        // 내 정보를 가지고
        // 내 정보 수정 페이지로 이동
        router.push({ name: "myPageModify" });
    }
}

onMounted(() => {
    let profile;

    if (props.others) {
        profile = userStore.getProfileById(props.id);
        console.log("니 프로필");
    }
    else if (route.params.memberId) {
        profile = userStore.getProfileById(route.params.memberId);
        console.log("니 프로필");
    } else {
        profile = userStore.getProfile();
        console.log("내 프로필");
    }

    profile.then((getValue) => {
        userProfile.value.nickname = getValue.nickname;
        userProfile.value.experiencePoint = getValue.experiencePoint;
        userProfile.value.rank = getValue.rank;

        if (getValue.profileImage === null) {
            userProfile.value.profileImage = sampleImage;
        } else {
            userProfile.value.profileImage = getValue.profileImage;
        }
    });
});
</script>

<template>
    <v-card
        width="auto"
        height="auto"
        class="d-flex align-center"
        max-height="700"
    >
        <v-row class="d-flex flex-column ma-6">
            <v-col class="d-flex justify-end">
                <v-select
                    max-width="45"
                    density="compact"
                    variant="solo"
                    :items="['로그아웃', '내 정보 수정']"
                    @update:modelValue="selectItem"
                ></v-select>
            </v-col>
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
                {{ userProfile.rank }}
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
