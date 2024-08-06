import GroupMemberDetail from "@/components/group/GroupMemberDetail.vue";
import GroupMemberDetailByAdmin from "@/components/group/GroupMemberDetailByAdmin.vue";
import MyPage from "@/components/user/MyPage.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import GroupListView from "@/views/group/GroupListView.vue";
import GroupView from "@/views/group/GroupView.vue";
import LoginView from "@/views/LoginView.vue";
import MainPageView from "@/views/MainPageView.vue";
import MyPageView from "@/views/MyPageView.vue";
import SignUpView from "@/views/SignUpView.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: "/",
      redirect: "/login", // 기본 경로를 로그인 페이지로 리디렉션
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/signup",
      name: "signUp",
      component: SignUpView,
    },
    {
      path: "/game-room",
      component: GameRoomView,
      children: [
        {
          path: ":roomId",
          name: "gameRoom",
          component: () => import("@/components/gameRoom/TheGameRoom.vue"),
        },
        {
          path: ":roomId/writing-game",
          name: "writingGame",
          component: () => import("@/views/WritingGameView.vue"),
        },
        {
          path: ":roomId/writing-game/result",
          name: "writingGameResult",
          component: () => import("@/views/WritingGameResultView.vue"),
        },

        {
          path: ":roomId/running-game",
          name: "runningGame",
          component: () => import("@/views/runningGame/RunningGameView.vue"),
        },
        {
          path: ":roomId/running-game/result",
          name: "runningGameResult",
          component: () =>
            import("@/views/runningGame/RunningGameResultView.vue"),
        },
      ],
    },
    {
      path: "/main-page",
      name: "mainPage",
      component: MainPageView,
    },
    {
      path: "/my-page",
      component: MyPageView,
      children: [
        {
          path: "",
          name: "myPage",
          component: MyPage,
        },
        {
          path: "modify",
          name: "myPageModify",
          component: () => import("@/components/user/MyPageModify.vue"),
        },
        {
          path: "modify/password",
          name: "myPageModifyPassword",
          component: () => import("@/components/user/PasswordModify.vue"),
        },
        {
          path: "book",
          name: "bookList",
          component: () => import("@/views/TaleListView.vue"),
        },
        {
          path: "book/:bookId",
          name: "bookDetail",
          component: () => import("@/views/TalesDetailView.vue"),
        },
      ],
    },
    {
      path: "/group",
      component: GroupView,
      children: [
        {
          path: "list",
          name: "groupList",
          component: GroupListView,
        },
        {
          path: "list/:groupId",
          name: "groupDetail",
          component: () => import("@/views/group/GroupDetailView.vue"),
        },
        {
          path: "create",
          name: "groupCreate",
          component: () => import("@/components/group/GroupCreate.vue"),
        },
        {
          path: ":groupId/modify",
          name: "groupModify",
          component: () => import("@/components/group/GroupModify.vue"),
        },
        {
          path: "member/:memberId",
          name: "groupMemberDetail",
          component: GroupMemberDetail,
        },
        {
          path: "member/:memberId",
          name: "groupMemberDetailByAdmin",
          component: GroupMemberDetailByAdmin,
          //그룹장만 입장 가능하게 해야 되맨!
        },
      ],
    },
  ],
});

export default router;
