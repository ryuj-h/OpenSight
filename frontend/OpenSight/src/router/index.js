import { createRouter, createWebHistory } from 'vue-router'
import FindEmail from '@/views/auth/FindEmail.vue'
import FindEmailComplete from '@/views/auth/FindEmailComplete.vue'
import FindEmailFail from '@/views/auth/FindEmailFail.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/find-email',
      name: 'FindEmail',
      component: FindEmail
    },
    {
      path: '/find-email/complete',
      name: 'FindEmailComplete',
      component: FindEmailComplete
    },
    {
      path: '/find-email/fail',
      name: 'FindEamilFail',
      component: FindEmailFail
    }
  ]
})

export default router
