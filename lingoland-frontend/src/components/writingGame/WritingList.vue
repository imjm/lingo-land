<script setup>
import { useOpenviduStore } from "@/stores/openvidu";
import { useWritingGameStore } from "@/stores/writingGame";
import { storeToRefs } from "pinia";
import Swal from "sweetalert2";
import { computed, ref, watch } from "vue";

const writingGameStore = useWritingGameStore();
const { turn, totalTime, pageCount } = storeToRefs(writingGameStore);

const openviduStore = useOpenviduStore();
const { findCurrentStoryId } = openviduStore;
const { storyList } = storeToRefs(openviduStore);

const currentStorys = ref(null);

// 내 커넥션 아이디를 찾고
// 현재 턴에 해당하는 이야기아이디를 찾고
// 찾은 이야기 아이디를 키로 갖는 아이템을 찾아야한다.
function setStory() {
    // 현재 내가 봐야할 스토리 아이디 찾기
    let currentStoryId = findCurrentStoryId();

    currentStorys.value = storyList.value[currentStoryId].story;
}

// watch를 써서 turn의 변화를 보고 있다가 turn이 바뀌면 보여주는 글을 바꾼다.
// turn이 변경됨을 감지
watch(turn, (newValue, oldValue) => {
    // setStory 함수 실행
    console.log("************turn이 바뀜", oldValue, newValue);

    if (newValue < pageCount.value) {
        // 턴이 바뀌면 alert하고 타이머 재시작 그리고 글쓰기 페이지
        Swal.fire({
            title: "새로운 이야기가 도착했어요",
            icon: "success",
            confirmButtonText: "완료",
        }).then(() => {
            setStory();

            console.log("****************current story", currentStorys);
            totalTime.value = 15;
        });
    }
});

const computedStorys = computed(() => {
    const storys =
        currentStorys.value === null
            ? ["나만의 이야기를 시작하세요"]
            : currentStorys.value;

    console.log("************storys: ", storys);
    return storys;
});
</script>

<template>
    <div
        v-for="(story, index) in computedStorys"
        :key="index"
        class="text-h6 text-white"
    >
        {{ story }}
    </div>
</template>

<style scoped></style>
