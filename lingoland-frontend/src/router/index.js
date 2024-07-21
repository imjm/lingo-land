import LoginView from "@/views/LoginView.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import SignUpView from "@/views/SignUpView.vue";
import GroupJoinView from "@/views/GroupJoinView.vue";
import GroupDetailView from "@/views/GroupDetailView.vue";
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
      path: "/groups",
      name: "groups",
      component: GroupJoinView,
    },
    {
      path: "/groupId",
      name: "groupId",
      component: GroupDetailView,
    },
  ],
});

export default router;
