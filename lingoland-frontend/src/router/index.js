import LoginView from "@/views/LoginView.vue";
import GameRoomView from "@/views/GameRoomView.vue";
import GameRoomFindView from "@/views/GameRoomFindView.vue";
import SignUpView from "@/views/SignUpView.vue";

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
      path: "/rooms/join",
      name: "joinRoom",
      component: GameRoomFindView,
    },
    {
      path: "/signup",
      name: "signUp",
      component: SignUpView,
    },
  ],
});

export default router;
