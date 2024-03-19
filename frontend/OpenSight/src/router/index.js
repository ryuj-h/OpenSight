import { createRouter, createWebHistory } from 'vue-router'
import IntroView from '@/views/intro/Intro.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Intro',
      component: IntroView
    },
  ]
})

export default router
