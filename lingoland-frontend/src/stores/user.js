import { defineStore } from "pinia";
import { inject } from "vue";
import { useRouter } from "vue-router";
import { httpStatus } from "@/apis/http-status";

export const useUserStore = defineStore("userStore", () => {
    const router = useRouter();
    const axios = inject("axios");

    // 페이지 토큰을 받아서 새로고침시에도 받아둔 토큰을 저장했다가 이리저리...
    // token을 기존 로그인과 비슷하게 만들었는데 이제 저장이 가능한...
    const token = localStorage.getItem("authToken");
    if (token) {
        axios.defaults.headers.common["Authorization"] = token;
    }

    const checkPassword = (originPassword, checkPassword) => {
        return originPassword === checkPassword;
    };

    const checkDuplicate = async (loginId) => {
        try {
            const response = await axios.get(`/users/check/${loginId}`, {
                withCredentials: true,
            });
            if (response.status === httpStatus.OK) {
                return true;
            }
        } catch (error) {
            if (error.response.status === httpStatus.CONFLICT) {
                return false;
            }
        }
        return false;
    };

    const signUp = async (userInfo) => {
        try {
            const response = await axios.post("/users/sign-up", userInfo, {
                withCredentials: true,
            });
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
        } catch (error) {
            console.log(error);
        }
    };

    const login = async (userInfo) => {
        try {
            const response = await axios.post("/login", userInfo, {
                withCredentials: true,
            });
            if (response.status === httpStatus.OK) {
                localStorage.setItem(
                    "authToken",
                    response.headers.authorization
                );
                axios.defaults.headers.common["Authorization"] =
                    response.headers.authorization;
                router.replace({ name: "mainPage" });
            }
        } catch (error) {
            console.log(error);
        }
    };

    const logout = () => {
        localStorage.removeItem("authToken");
        delete axios.defaults.headers.common["Authorization"];
        router.replace({ name: "login" });
    };

    const getProfile = async () => {
        try {
            const response = await axios.get("/users", {
                withCredentials: true,
            });
            if (response.status === httpStatus.OK) {
                return response.data;
            }
        } catch (error) {
            console.log(error);
        }
    };

    return {
        checkPassword,
        checkDuplicate,
        signUp,
        login,
        logout,
        getProfile,
    };
});
