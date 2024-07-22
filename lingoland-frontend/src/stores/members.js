import { ref, computed } from "vue";
import { defineStore } from "pinia";
// import axios from 'axios'

export const useMemberStore = defineStore("member", () => {
  // const members = ref([]);
  const selectedMember = ref(null);

  const members = [
    {
      avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
      name: "김초딩",
      rank: "정1품",
      description: "안녕하세요. 반갑습니다",
    },
    {
      avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
      name: "이초딩",
      rank: "종6품",
      description: "안녕하세요. 반갑습니다",
    },
    {
      avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
      name: "박초딩",
      rank: "정3품",
      description: "안녕하세요. 반갑습니다",
    },
    {
      avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
      name: "최초딩",
      rank: "정1품",
      description: "안녕하세요. 반갑습니다",
    },
  ];

  // 클릭 했을 경우 선택된 그룹창을 보여주기 위함
  const clickMember = (member) => {
    selectedMember.value = member;
  };

  return { members, clickMember, selectedMember };
});
