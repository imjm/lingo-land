import { inject } from "vue";
import { defineStore } from "pinia";

export const useProblemStore = defineStore("problemStore", () => {
    /**
     * State
     */

    const axios = inject("axios");

    /**
     * actions
     */
    const getMyWrongProblems = async () => {
        const myWrongProblemList = await axios
            .get(`/problems/wrong-problems`)
            .then((response) => {
                return Promise.resolve(response.data);
            })
            .catch((error) => {
                console.log(error);
            });

        return myWrongProblemList;
    };

    return { getMyWrongProblems };
});
