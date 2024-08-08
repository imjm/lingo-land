<script setup>
import WritingInput from "@/components/writingGame/WritingInput.vue";
import WritingList from "@/components/writingGame/WritingList.vue";
import { useOpenviduStore } from "@/stores/openvidu";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";

const openviduStore = useOpenviduStore();

const { OV, session } = openviduStore;
const { participants } = storeToRefs(openviduStore);

// 제출을 눌렀을 때 시그널을 송신

// 제출을 눌렀을 때 시그널을 수신

onMounted(() => {
    // 참가자 배열 만큼 이야기 배열을 만듬

    // 이야기 마다 작성할 유저의 순서를 미리 만들기
    // 방장의 순서를 기준으로 만든다 -> 유저마다 참가자 배열의 순서가 다르기 때문
    const storyOrdering = ref([
        {
            storyId: 1,
            order: [1, 2, 3, 4, 5],
            visited: [false, false, false, false, false],
        },
        {
            storyId: 2,
            order: [2, 3, 4, 5, 1],
            visited: [false, false, false, false, false],
        },
        {
            storyId: 3,
            order: [3, 4, 5, 1, 2],
            visited: [false, false, false, false, false],
        },
        {
            storyId: 4,
            order: [4, 5, 1, 2, 3],
            visited: [false, false, false, false, false],
        },
        {
            storyId: 5,
            order: [5, 1, 2, 3, 4],
            visited: [false, false, false, false, false],
        },
    ]);

    // 이야기 배열 구성
    const storyList = ref([
        {
            storyId: 1,
            story: [
                {
                    content: "첫번째 스토리",
                },
                {
                    content: "두번째 스토리",
                },
            ],
        },
        {
            storyId: 2,
            story: [
                {
                    content: "첫번째 스토리",
                },
                {
                    content: "두번째 스토리",
                },
            ],
        },
        {
            storyId: 3,
            story: [
                {
                    content: "첫번째 스토리",
                },
                {
                    content: "두번째 스토리",
                },
            ],
        },
    ]);
});
</script>

<template>
    <v-main
        class="background-image d-flex flex-column align-center justify-center"
    >
        <div class="text-h3 text-white my-16 pt-5">
            다음 글을 읽고 이어질 이야기를 작성하세요
        </div>

        <v-row class="d-flex justify-space-evenly">
            <v-col class="d-flex ma-10 pa-10 align-start">
                <WritingList />
            </v-col>
            <v-col class="d-flex ma-10 pa-5 align-start">
                <WritingInput />
            </v-col>
        </v-row>
    </v-main>
</template>

<style scoped>
.background-image {
    background-image: url("@/assets/blackboard.png");
    background-size: contain;
    background-position: center;
    background-repeat: no-repeat;
    height: 100vh;
    width: 100%;
}
</style>
