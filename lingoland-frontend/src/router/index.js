import MyPageModify from "@/components/user/MyPageModify.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import GroupDetailView from "@/views/GroupDetailView.vue";
import GroupJoinView from "@/views/GroupJoinView.vue";
import LoginView from "@/views/LoginView.vue";
import MainPageView from "@/views/MainPageView.vue";
import MyPageView from "@/views/MyPageView.vue";
import SignUpView from "@/views/SignUpView.vue";
import WritingGameResultView from "@/views/WritingGameResultView.vue";
import WritingGameView from "@/views/WritingGameView.vue";

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
        {
            path: "/groups",
            name: "groups",
            component: GroupJoinView,
        },
        {
            path: "/groupId",
            name: "groupId",
            component: GroupDetailView,
        },
        {
            path: "/main-page",
            name: "mainPage",
            component: MainPageView,
        },
        {
            path: "/my-page",
            name: "myPage",
            component: MyPageView,
            children: [
                {
                    path: "modify",
                    name: "myPageModify",
                    component: MyPageModify,
                },
            ],
        },
    ],
});

export default router;
