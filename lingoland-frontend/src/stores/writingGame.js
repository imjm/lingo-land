import { httpStatus } from "@/configuration/http-status";
import { writingGameConfiguration } from "@/configuration/writingGameConf";
import { defineStore } from "pinia";
import swal from "sweetalert2";
import { inject, ref } from "vue";

export const useWritingGameStore = defineStore("writingGameStore", () => {
    /**
     * State
     */
    window.Swal = swal;

    const axios = inject("axios");
    const pageCount = ref(0);
    const turn = ref(0);
    const totalTime = ref(writingGameConfiguration.gameTime);

    /**
     * actions
     */

    // 글쓰기 게임 시작 API 요청
    const setWritingGame = async (sessionId, startDTO) => {
        await axios
            .post(`/writing-game/start/${sessionId}`, startDTO, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    console.log(response);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    // 글쓰기 게임 단계별 제출
    // true를 받으면 다음 턴으로 진행
    const submitStory = async (sessionId, storyDTO) => {
        const reseult = await axios
            .post(`/writing-game/request/${sessionId}`, storyDTO, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    console.log("***********글쓰기 게임 제출", response);
                    // 제출 요청에 대해 true가 오면 turn을 바꿈
                    if (response.data.goNext) {
                        turn.value++;
                    }
                    // 마지막 턴이면 fairyTales 에 데이터가 있다. , 아니면 빈 배열
                    // 모든 사람들의 동화인데 마지막 턴 글은 없다.
                    if (response.data.fairyTales) {
                        console.log(
                            "*************마지막 동화목록입니다.",
                            response.data
                        );
                        return response.data.fairyTales;
                    }
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return reseult;
    };

    // 글쓰기 게임 첫 턴일 때 제목 제출
    const submitTitle = async (sessionId, title) => {
        const result = await axios
            .post(`/writing-game/title/${sessionId}`, title, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.NOCONTENT) {
                    console.log("***********글쓰기 게임 제목 제출", response);
                    return true;
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return result;
    };

    return {
        pageCount,
        turn,
        totalTime,
        setWritingGame,
        submitStory,
        submitTitle,
    };
});
