import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
// import { useGameStore } from './gameStore';
import { gameRanks } from './question';
// import { useProShuffleStore } from './proshuffleStore';
import { storeToRefs } from 'pinia';
// const proShuffleStore = useProShuffleStore();

// const randomModels = proShuffleStore.randomModels;
// let mixModels

export const useShuffleStore = defineStore('shuffleStore', () => {
    // const gameStore = useGameStore();
    // const gameRanks = computed(() => gameStore.gameRanks); // or however you get ranks
// console.log('@@@@@@@randommodels',proShuffleStore)
        // const mixModels = randomModel
    // const sortedRanks = computed(() => {
    //     return [...gameRanks.value].sort((a, b) => b.score - a.score);
    // });

    return {};
});