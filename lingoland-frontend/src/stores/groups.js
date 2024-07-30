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

  const groups = ref([]);
  const selectedGroup = ref(null);

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
            router.push({ name: "groupDetail", params : {groupId : 'back에서 줘야할듯'} });
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

  // group 불러오기
  const getGroup = async () => {
    await axios
        .get("/groups", { withCredentials: true })
        .then((response) => {
            console.log(response) 
        })
        .catch((error) => {
            console.log(error);
        });
  };

  // 클릭 했을 경우 선택된 그룹창을 보여주기 위함
  const clickGroup = (group) => {
    selectedGroup.value = group;
  };

  return { groups, getGroups, clickGroup, selectedGroup, checkDuplicate, createGroup };
});
