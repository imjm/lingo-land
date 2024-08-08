import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
// import { useGameStore } from './gameStore';
import { gameRanks } from './question';

const models = [
    { name: "chick", path: "/cute_chick/scene.gltf", scale: 1, rotation: -Math.PI / 2 },
    { name: "squid", path: "/squid/scene.gltf", scale: 10, rotation: 0 },
    { name: "dragonfly", path: "/dragonfly/scene.gltf", scale: 3, rotation: -Math.PI},
    { name: "unicorn", path: "/just_a_unicorn/scene.gltf", scale: 0.5, rotation: 0 },
    { name: "dog", path: "/stylized_dog_low_poly/scene.gltf", scale: 3, rotation: 0 },
    { name: "cat", path: "/little_cat/scene.gltf", scale: 7, rotation: 0 },
];
// let randomModels

const useProShuffleStore = defineStore('proShuffleStore', () => {
    // const gameStore = useGameStore();
    // const gameRanks = computed(() => gameStore.gameRanks); // or however you get ranks

        const randomModels = [...models];
        for (let i = randomModels.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [randomModels[i], randomModels[j]] = [randomModels[j], randomModels[i]];
        }
    // const sortedRanks = computed(() => {
    //     return [...gameRanks.value].sort((a, b) => b.score - a.score);
    // });

    return { randomModels };
});
const randomrandomModels = useProShuffleStore
export {
    randomrandomModels
}