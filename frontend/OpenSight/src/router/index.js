<<<<<<< HEAD
import { createRouter, createWebHistory } from 'vue-router'

=======
import { createRouter, createWebHistory } from 'vue-router';
import IntroView from '@/views/intro/Intro.vue';
import LoginView from '@/views/auth/Login.vue';
import MainView from '@/views/dashboard/Main.vue';
>>>>>>> feature/FE-socialLogin

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
<<<<<<< HEAD
    
=======
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/main',
      name: 'Main',
      component: MainView
    }
>>>>>>> feature/FE-socialLogin
  ]
})

export default router
