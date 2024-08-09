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

    // 참가자마다 작성할 이야기 순서를 만듬
    // 방장의 순서를 기준으로 만든다 -> 유저마다 참가자 배열의 순서가 다르기 때문

    // storyOrdering에서 내 커넥션 아이디를 찾는다.
    // 내 커넥션 아이디를 찾으면 storyOrderList에서 turn에 해당하는 이야기Id를 찾는다.

    // 찾은 이야기Id를 가지고 storyList에 해당하는 Id에 context를 출력한다.
    const turn = ref(0);


    const storyOrdering = ref([
        {
            connectionId: 1,
            storyOrderList: [1, 2, 3, 4, 5], // storyId
        },
        {
            connectionId: 2,
            storyOrderList: [2, 3, 4, 5, 1],
        },
        {
            connectionId: 3,
            storyOrderList: [3, 4, 5, 1, 2],
        },
        {
            connectionId: 4,
            storyOrderList: [4, 5, 1, 2, 3],
        },
        {
            connectionId: 5,
            storyOrderList: [5, 1, 2, 3, 4],
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

// 세션에 스트림이 생성될 때 호출되는 콜백 함수
session.on("signal:submitted", function (event) {
    // event.target.connectionId // 내 커넥션아이디
    // event.data 

    // 내 커넥션 아이디랑
    // storyOrdering에서 내 커넥션 아이디를 찾는다.
    // 내 커넥션 아이디를 찾으면 storyOrderList에서 turn에 해당하는 이야기Id를 찾는다.

    // 찾은 이야기Id를 가지고 storyList에 해당하는 Id에 context를 출력한다.



});


session.on()


</script>

<template>
    <v-main class="background-image gowun-batang-regular pa-10">
        <v-row>
            <v-col class="d-flex flex-column justify-center align-center">
                <div style="font-size: 20px; color: white">
                    지금까지 쓴 글입니다.
                </div>

                <WritingList />
            </v-col>
            <v-col class="d-flex flex-column justify-center align-center">
                <WritingInput />
            </v-col>
        </v-row>
    </v-main>
</template>

<style>
.background-image {
    background-image: url("@/assets/blackboard.png");
    background-size: cover;
    background-position: center 75%;
    background-repeat: no-repeat;
    height: 100vh;
    width: 100vw;
    background-color: black;
    overflow: hidden;
}
</style>
