import GameRoomView from "@/views/GameRoomView.vue";
import GroupDetailView from "@/views/GroupDetailView.vue";
import GroupJoinView from "@/views/GroupJoinView.vue";
import LoginView from "@/views/LoginView.vue";
import MainPageView from "@/views/MainPageView.vue";
import MyPageModifyView from "@/views/MyPageModifyView.vue";
import SignUpView from "@/views/SignUpView.vue";
import WritingGameResultView from "@/views/WritingGameResultView.vue";
import WritingGameView from "@/views/WritingGameView.vue";
import GroupManageView from "@/views/GroupManageView.vue";
import GroupCreate from "@/components/group/GroupCreate.vue";

import { createRouter, createWebHistory } from "vue-router";
import GroupModify from "@/components/group/GroupModify.vue";

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
            path: "/my-page-modify",
            name: "myPageModify",
            component: MyPageModifyView,
        },
        {
            path: "/group",
            component: GroupManageView,
            children: [
                {
                    path: "create",
                    name: "groupCreate",
                    component: GroupCreate,
                },
                {
                    path: "modify",
                    name: "groupModify",
                    component: GroupModify,
                },
            ],
        },
    ],
});

export default router;
