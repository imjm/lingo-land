import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";

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
      path: "",
      name: "home",
      component: HomeView,
    },
  ]
});

export default router;
