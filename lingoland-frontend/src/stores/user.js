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
        // 비밀번호와 비밀번호 확인이 일치한지 확인
        if (originPassword === checkPassword) {
            return true;
        } else {
            return false;
        }
    }

    // id 중복 체크
    const checkDuplicate = async (loginId) => {
        let return_value;
        await axios
            .get(`/users/check/${loginId}`, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return_value = true;
                }
            })
            .catch((error) => {
                if (error.status === httpStatus.CONFLICT) {
                    return_value = false;
                }
            });

        return return_value;
    };

    // 회원가입
    const signUp = async (userInfo) => {
        await axios
            .post("/users/sign-up", userInfo, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.CREATE) {
                    Swal.fire({
                        title: "회원가입 완료",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then(() => {
                        router.replace({ name: "login" });
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

    // 로그인
    const login = async (userInfo) => {
        await axios
            .post("/login", userInfo, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    axios.defaults.headers.common["Authorization"] =
                        response.headers.authorization;
                    router.replace({ name: "mainPage" });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return {
        checkPassword,
        checkDuplicate,
        signUp,
        login,
    };
});
