// src/stores/gameStore.js
import { defineStore, storeToRefs } from "pinia";
import { computed, inject, ref } from "vue";
import { useOpenviduStore } from "../openvidu";

export const useGameStore = defineStore("gameStore", () => {
    const axios = inject("axios");

    const openviduStore = useOpenviduStore();
    const { reparticipants } = storeToRefs(openviduStore);

    const zCoordinate = ref(0); // z 위치
    const zDivided = computed(() => zCoordinate.value / 75); // 게임진행률

    const isGameEnded = ref(false); // 게임 종료

    const gameRanks = ref([]);
    const sortedRanks = computed(() => {
        return [...gameRanks.value].sort((a, b) => b.score - a.score);
    });

    const coinScore = ref(0);
    const coinTotalScore = ref(0);

    const wrongProblem = ref([]);

    function updateZCoordinate(z) {
        zCoordinate.value = z;
    }
    function endGame() {
        isGameEnded.value = true;
    }

    function saveResult(problemList) {
        axios
            .post("/problems/save-results", problemList, {
                withCredentials: true,
            })
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    function setGameRanks() {
        for (const participant of reparticipants.value) {
            gameRanks.value.push({
                connectionId: participant.connectionId,
                userId: participant.userId,
                score: 0,
                coin: 0,
                nickname: participant.userProfile.nickname,
                problemList: wrongProblem,
            });
        }
    }

    function resetRunningGame() {
        zCoordinate.value = 0;
        isGameEnded.value = false;
        gameRanks.value = [];
        wrongProblem.value = [];
        coinScore.value = 0;
        coinTotalScore.value = 0;

        setGameRanks();
    }

    return {
        zCoordinate,
        zDivided,
        isGameEnded,
        gameRanks,
        sortedRanks,
        wrongProblem,
        coinScore,
        coinTotalScore,
        updateZCoordinate,
        endGame,
        saveResult,
        setGameRanks,
        resetRunningGame,
    };
});
