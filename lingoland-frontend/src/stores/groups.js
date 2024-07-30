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

    /**
     * actions
     */

    //그룹명 중복체크
    const checkDuplicate = async (groupName) => {
        let return_value;
        console.log(groupName);
        await axios
            .get(`/groups/check/${groupName}`, { withCredentials: true })
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
    const modifyGroup = async (groupInfo) => {
        console.log(groupInfo);
        await axios
            .put("/groups", groupInfo, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.CREATE) {
                    Swal.fire({
                        title: "그룹 정보 수정 성공!",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then((response) => {
                        console.log(response.data.groupId);
                        router.replace({
                            name: "groupDetail",
                            params: { groupId: response.data.groupId },
                        });
                    });
                } else {
                    Swal.fire({
                        title: "그룹 정보 수정 실패",
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

    // const groups = ref([]);
    const selectedGroup = ref(null);

    const groups = [
        {
            name: "2024년 3학년 1반",
            description: "세상에서 제일 머찐 3학년 1반 모여라~!",
        },

        {
            name: "2023년 2학년 8반",
            description: "행복한.하루.되세요~~~!!! @>------",
        },
        {
            name: "2023년 2학년 7반",
            description: "행복한.하루.되세요~~~!!! @>------",
        },
        {
            name: "2023년 2학년 6반",
            description: "행복한.하루.되세요~~~!!! @>------",
        },
    ];

    const getGroups = async () => {
        await axios
            .get("/groups", { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    console.log(response);
                }
            })
            .catch((error) => {
                if (error.status === httpStatus.CONFLICT) {
                }
            });
    };

    // 클릭 했을 경우 선택된 그룹창을 보여주기 위함
    const clickGroup = (group) => {
        selectedGroup.value = group;
    };

    return { modifyGroup, createGroup, checkDuplicate, groups, clickGroup, getGroups, selectedGroup };
});
