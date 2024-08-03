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

    // 신분명이 결정되면 수정 예정
    const statue = ref([
        "신분1",
        "신분2",
        "신분3",
        "신분4",
        "신분5",
        "신분6",
        "신분7",
        "신분9",
        "신분10",
    ]);

    /**
     * actions
     */
    // 경험치를 어떤 기준으로 신분을 나눌 것인지 결정되면 수정 예정
    function getstatue(experiencePoint) {
        console.log("*****************경험치", experiencePoint);
        console.log(
            "*****************신분",
            statue.value[experiencePoint / 100]
        );

        return statue.value[experiencePoint / 100];
    }

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
                    // axios 요청 header에 access token 추가
                    axios.defaults.headers.common["Authorization"] =
                        response.headers.authorization;

                    // localStorage에 access token 추가
                    sessionStorage.setItem(
                        "accessToken",
                        response.headers.authorization
                    );

                    router.replace({ name: "mainPage" });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const logout = async () => {
        await axios
            .put("/logout", {}, { withCredentials: true })
            .then((response) => {
                console.log(response);
                sessionStorage.removeItem("accessToken");
                delete axios.defaults.headers.common["Authorization"];
                router.replace({ name: "login" });
            })
            .catch((error) => {
                console.log(error);
            });
    };

    // 유저 프로필 조회
    const getProfile = async () => {
        const userProfile = await axios
            .get("/users", { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return userProfile;
    };

    // 유저 프로필 조회(loginId 기준)
    const getProfileById = async (loginId) => {
        const userProfile = await axios
            .get(`/users/${loginId}`, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                console.log(error);
            });

        return userProfile;
    };

    const modifyNickname = async (newNickname) => {
        await axios
            .put(
                "/users/nickname",
                { nickname: newNickname },
                { withCredentials: true }
            )
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    console.log(response);
                    router.replace({ name: "myPage" });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return {
        getstatue,
        checkPassword,
        checkDuplicate,
        signUp,
        login,
        logout,
        getProfile,
        getProfileById,
        modifyNickname,
    };
});
