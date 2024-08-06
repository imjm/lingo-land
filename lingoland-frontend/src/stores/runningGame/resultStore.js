import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { useGameStore } from './gameStore';
import { gameRanks } from './question';
export const useResultStore = defineStore('resultStore', () => {
    const gameStore = useGameStore();
    // const gameRanks = computed(() => gameStore.gameRanks); // or however you get ranks

    const sortedRanks = computed(() => {
        return [...gameRanks.value].sort((a, b) => b.score - a.score);
    });

    return { sortedRanks };
});