import { httpStatus } from "@/apis/http-status";
import { defineStore } from "pinia";
import swal from "sweetalert2";
import { inject, ref } from "vue";
import { useRouter } from "vue-router";

export const useGroupStore = defineStore("group", () => {
    /**
     * State
     */
    window.Swal = swal;

    const router = useRouter();
    const axios = inject("axios");

    const groupNameFormat = ref(false);
    const passwordFormat = ref(false);
    const nameDuplicate = ref(false);
    const passwordCheck = ref(false);
    const groupDiscriptionFormat = ref(false);

    /**
     * actions
     */
    function checkPassword(checkPassword, originPassword) {
        if (originPassword === checkPassword) {
            passwordCheck.value = false;
        } else {
            passwordCheck.value = true;
        }
    }

    // 비밀번호 포멧체크
    function validatePasswordFormat(password) {
        // 비밀번호 최대길이 4

        // 형식이 일치함
        if (password.length === 4) {
            passwordFormat.value = false;
        } else {
            passwordFormat.value = true;
        }
    }

    // 그룹 이름 포맷 체크
    function validateGroupNameFormat(groupName) {
        // 그룹 이름 길이 3- 20 한글,영어,숫자/-/_
        const groupNameRegex = /^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$/;

        // 형식이 일치함
        if (groupNameRegex.test(groupName)) {
            groupNameFormat.value = false;
        } else {
            groupNameFormat.value = true;
        }
    }

    // 그룹 소개 포맷 체크
    function validateGroupDiscriptionFormat(groupDiscription) {
        // 그룹 소개 길이 최대 200자

        // 형식이 일치함
        if (groupDiscription.length <= 200) {
            groupDiscriptionFormat.value = false;
        } else {
            groupDiscriptionFormat.value = true;
        }
    }

    //그룹명 중복체크
    const checkDuplicate = async (groupName) => {
        await axios
            .get(`/groups/check/${groupName}`, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    nameDuplicate.value = false;
                    Swal.fire({
                        title: "중복확인 완료",
                        icon: "success",
                        confirmButtonText: "완료",
                    });
                }
            })
            .catch((error) => {
                if (error.response.status === httpStatus.CONFLICT) {
                    nameDuplicate.value = true;
                    Swal.fire({
                        title: "중복된 그룹 이름이 있어요",
                        icon: "error",
                    });
                }
            });
    };

    // 그룹 등록
    const createGroup = async (groupInfo) => {
        console.log(groupInfo);
        await axios
            .post("/groups", groupInfo, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.CREATE) {
                    Swal.fire({
                        title: "그룹 만들기 성공!",
                        icon: "success",
                        confirmButtonText: "완료",
                    });
                    console.log(response);
                    console.log("나는 성공했는데 니네가 안보낸 거임");
                    router.replace({
                        name: "groupDetail",
                        params: { groupId: response.data.groupId },
                    });
                } else {
                    Swal.fire({
                        title: "그룹 만들기 실패",
                        icon: "error",
                    });
                }
            })
            .catch((error) => {
                Swal.fire({
                    title: "그룹 만들기 실패",
                    icon: "error",
                });
                console.log(error);
            });
    };

    // 그룹 수정
    const modifyGroup = async (groupInfo, groupImage) => {
        const formData = new FormData();
        formData.append(
            "updateGroup",
            new Blob([JSON.stringify(groupInfo)], { type: "application/json" })
        );
        if (groupImage) {
            formData.append("groupImage", groupImage);
        }

        axios
            .put(`/groups/${groupInfo.id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.NOCONTENT) {
                    Swal.fire({
                        title: "그룹 정보 수정 성공!",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then(() => {
                        router.replace({
                            name: "groupDetail",
                            params: { groupId: groupInfo.id },
                        });
                    });
                } else {
                    Swal.fire({
                        title: "response 실패",
                        icon: "error",
                    });
                }
            })
            .catch((error) => {
                Swal.fire({
                    title: "그룹 정보 수정 실패",
                    icon: "error",
                });
                console.log(error);
            });
    };

    // 그룹 리스트 조회
    const getGroups = async () => {
        const groupList = await axios
            .get("/groups", { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                if (error.status === httpStatus.CONFLICT) {
                }
            });

        return groupList;
    };

    // 단일 그룹 조회
    const getGroup = async (groupId) => {
        const group = await axios
            .get(`/groups/${groupId}`, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                if (error.status === httpStatus.CONFLICT) {
                }
            });
        return group;
    };

    // 내가 속한 그룹 조회
    const getMyGroups = async () => {
        const myGroupList = await axios
            .get("/groups/users", { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return Promise.resolve(response.data);
                }
            })
            .catch((error) => {
                if (error.status === httpStatus.CONFLICT) {
                }
            });

        return myGroupList;
    };

    // 그룹 가입하기
    const joinGroup = async (groupId, joinInfo) => {
        const joinGroupResult = await axios
            .post(`/groups/${groupId}/users`, joinInfo, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.NOCONTENT) {
                    console.log(response);
                    Swal.fire({
                        title: "그룹가입 완료",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then(() => {
                        router.replace({ name: "myPage" });
                    });
                }
            })
            .catch((error) => {
                console.log(error);
                Swal.fire({
                    title: "그룹가입 실패",
                    icon: "error",
                });
            });

        return joinGroupResult;
    };

    // 그룹장 확인
    const checkGroupLeader = async (groupId) => {
        await axios
            .get(`/groups/${groupId}/check-leader`, {
                withCredentials: true,
            })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    if (response.data) {
                        router.push({ name: "groupModify", params: groupId });
                    } else {
                        Swal.fire({
                            title: "그룹장이 아닙니다.",
                            icon: "error",
                        });
                    }
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return {
        groupNameFormat,
        passwordFormat,
        passwordCheck,
        nameDuplicate,
        groupDiscriptionFormat,
        checkPassword,
        validatePasswordFormat,
        validateGroupDiscriptionFormat,
        validateGroupNameFormat,
        modifyGroup,
        createGroup,
        checkDuplicate,
        getGroups,
        getGroup,
        getMyGroups,
        joinGroup,
        checkGroupLeader,
    };
});
