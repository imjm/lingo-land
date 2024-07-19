import { ref, computed } from "vue";
import { defineStore } from "pinia";
// import axios from 'axios'

export const useGroupStore = defineStore("group", () => {
  const groups = ref([]);
  const selectedGroup = ref(null);

  const groupOrdering = () => {
    /* data 받아오는 거 해야할 듯 정렬해서 받아오는 알고리즘? */
  };
  // 클릭 했을 경우 선택된 그룹창을 보여주기 위함
  const clickGroup = (group) => {
    selectedGroup.value = group;
  };

  return { groups, clickGroup, selectedGroup };
});
