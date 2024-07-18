import LoginView from '@/views/LoginView.vue'
import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path : '/login',
      name : 'login',
      components : LoginView
    },

   
  ]
})

export default router
