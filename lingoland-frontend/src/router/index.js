import LoginView from "@/views/LoginView.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import SignUpView from "@/views/SignUpView.vue";
import MainPageView from "@/views/MainPageView.vue";

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
      path: "/main-page",
      name: "mainPage",
      component: MainPageView,
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
  ],
});

export default router;
