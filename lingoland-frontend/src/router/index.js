import GameRoomView from "@/views/GameRoomView.vue";
import LoginView from "@/views/LoginView.vue";
import SignUpView from "@/views/SignUpView.vue";
import WritingGameView from "@/views/WritingGameView.vue";
import WritingGameResultView from "@/views/WritingGameResultView.vue";

import { createRouter, createWebHistory } from "vue-router";

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
        {
            path: "/writingGameResult",
            name: "writingGameResult",
            component: WritingGameResultView,
        },
    ],
});

export default router;
