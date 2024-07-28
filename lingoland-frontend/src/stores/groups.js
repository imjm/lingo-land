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
        await axios
            .get(`/group/check/${groupName}`, { withCredentials: true })
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
        await axios
            .post("/groups", groupInfo, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.CREATE) {
                    Swal.fire({
                        title: "그룹 만들기 성공!",
                        icon: "success",
                        confirmButtonText: "완료",
                    }).then(() => {
                        router.push({
                            name: "groupDetail",
                            params: { groupId: "back에서 줘야할듯" },
                        });
                    });
                } else {
                    Swal.fire({
                        title: "그룹 만들기 실패",
                        icon: "error",
                    });
                }
            })
            .catch((error) => {
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
    const groupOrdering = () => {
        /* data 받아오는 거 해야할 듯 정렬해서 받아오는 알고리즘? */
    };
    // 클릭 했을 경우 선택된 그룹창을 보여주기 위함
    const clickGroup = (group) => {
        selectedGroup.value = group;
    };

    return { groups, clickGroup, selectedGroup };
});
