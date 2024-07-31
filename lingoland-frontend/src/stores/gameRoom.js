import { defineStore } from "pinia";
import { httpStatus } from "@/apis/http-status";
import swal from "sweetalert2";
import { inject, ref } from "vue";
import { useRouter } from "vue-router";

export const useGameRoomStore = defineStore("gameRoom", () => {
    window.Swal = swal;

    const axios = inject("axios");

    const getSession = async () => {
        const sessionId = await axios
            .post("sessions", { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return sessionId;
    };

    const getToken = async (sessionId) => {
        const tokenId = await axios
            .post(`/sessions/${sessionId}/connections`, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return tokenId;
    };

    return {
        getSession,
        getToken,
    };
});
