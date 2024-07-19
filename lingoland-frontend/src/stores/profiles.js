import { ref, computed } from "vue";
import { defineStore } from "pinia";
// import axios from 'axios'

export const useProfileStore = defineStore("profile", () => {
  const profiles = ref([]);
  const selectedProfile = ref(null);

  const profileOrdering = () => {
    /* data 받아오는 거 해야할 듯 정렬해서 받아오는 알고리즘? */
  };
  // 클릭 했을 경우 선택된 프로필을 보여주기 위함
  const clickProfile = (profile) => {
    selectedProfile.value = profile;
  };

  return { profiles, clickProfile, selectedProfile };
});
