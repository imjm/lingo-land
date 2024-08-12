// src/stores/gameStore.js
import { defineStore } from "pinia";
import { inject, ref } from "vue";

export const useGameStore = defineStore("gameStore", {
  state: () => ({
    zCoordinate: ref(0),
    isGameEnded: false,
    axios: inject("axios"),
  }),

  actions: {
    updateZCoordinate(z) {
      this.zCoordinate = z;
    },
    endGame() {
      this.isGameEnded = true;
    },

    saveResult(problemList) {
      this.axios
        .post("/problems/save-results", problemList, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
});
