// src/stores/gameStore.js
import { defineStore } from 'pinia';
import { ref } from 'vue';


export const useGameStore = defineStore("gameStore", {
    state: () => ({
        zCoordinate: ref(0),
        isGameEnded: false,
    }),
    actions: {
        updateZCoordinate(z) {
            this.zCoordinate = z;
        },
        endGame() {
            this.isGameEnded = true;
        },
    },
});