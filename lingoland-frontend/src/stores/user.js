import { httpStatus } from "@/apis/http-status";
import { defineStore } from "pinia";
import swal from "sweetalert2";
import { inject, ref } from "vue";
import { useRouter } from "vue-router";

export const useUserStore = defineStore("userStore", () => {
    /**
     * State
     */
    window.Swal = swal;

    const router = useRouter();
    const axios = inject("axios");

    /**
     * actions
     */

    function checkPassword(originPassword, checkPassword) {
        if (originPassword === checkPassword) {
            return true;
        } else {
            return false;
        }
    }

    const signUp = async (userInfo) => {
        await axios
            .post("/users/sign-up", userInfo, { withCredentials: true })
            .then((response) => {
                console.log(
                    response.status,
                    httpStatus.CREATE,
                    response.status === httpStatus.CREATE
                );
                if (response.status === httpStatus.CREATE) {
                    Swal.fire({
                        title: "회원가입 완료",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then(() => {
                        router.push({ name: "login" });
                    });
                } else {
                    Swal.fire({
                        title: "회원가입 실패",
                        icon: "error",
                    });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return {
        checkPassword,
        signUp,
    };
});
