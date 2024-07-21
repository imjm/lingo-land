import LoginView from "@/views/LoginView.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import SignUpView from "@/views/SignUpView.vue";

import { createRouter, createWebHistory } from "vue-router";
import WritingGameView from "@/views/WritingGameView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),

    routes: [
        {
            path: "/login",
            name: "login",
            component: LoginView,
        },
        {
            path: "/rooms",
            name: "gameRoom",
            component: GameRoomView,
        },
        {
            path: "/signup",
            name: "signUp",
            component: SignUpView,
        },
        {
            path: "/writingGame",
            name: "writingGame",
            component: WritingGameView,
        },
    ],
});

export default router;
