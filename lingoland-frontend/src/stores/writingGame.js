import { httpStatus } from "@/apis/http-status";
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

    /**
     * actions
     */
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

    return { pageCount, turn, setWritingGame };
});
