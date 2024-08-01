// src/stores/gameStore.js
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useGameStore = defineStore('gameStore', () => {
    const zCoordinate = ref(0);

    function updateZCoordinate(newZ) {
        zCoordinate.value = newZ;
    }

    return {
        zCoordinate,
        updateZCoordinate,
    };
});
