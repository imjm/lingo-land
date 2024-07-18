import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: "/login",
      name: "login",
      components: LoginView,
    },

    {
      path: "/",
      name: "home",
      component: HomeView,
    },
  ],
});

export default router;
