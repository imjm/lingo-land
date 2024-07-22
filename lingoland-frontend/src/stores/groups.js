import { ref, computed } from "vue";
import { defineStore } from "pinia";
// import axios from 'axios'

export const useGroupStore = defineStore("group", () => {
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
