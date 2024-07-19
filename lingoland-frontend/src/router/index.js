import LoginView from "@/views/LoginView.vue";
import SignUpView from "@/views/SignUpView.vue";

import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),

    routes: [
        {
            path: "/signup",
            name: "signUp",
            component: SignUpView,
        },
        {
            path: "/login",
            name: "login",
            component: LoginView,
        },
    ],
});

export default router;
