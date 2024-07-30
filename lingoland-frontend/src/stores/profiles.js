import { ref, computed, inject } from "vue";
import { defineStore } from "pinia";
import { httpStatus } from "@/apis/http-status";
import swal from "sweetalert2";
import { useRouter } from "vue-router";
// import axios from 'axios'

export const useProfileStore = defineStore("profile", () => {
    /**
     * State
     */
    window.Swal = swal;

    const router = useRouter();
    const axios = inject("axios");

    /**
     * actions
     */

    const profiles = ref([]);
    const selectedProfile = ref(null);

    // 클릭 했을 경우 선택된 프로필을 보여주기 위함
    const clickProfile = async (profile) => {
        let return_value;
        await axios
            .get(`/users/${userId}`, { withCredentials: true })
            .then((response) => {
                if (response.status === httpStatus.OK) {
                    return_value = false;
                }
            });
        selectedProfile.value = profile;
        return return_value;
    };

    return { profiles, clickProfile, selectedProfile };
});
