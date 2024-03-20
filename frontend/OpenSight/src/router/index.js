import { createRouter, createWebHistory } from 'vue-router'
import PasswordFindView from '@/views/auth/PasswordFind.vue'
import PasswordFindFailView from '@/views/auth/PasswordFindFail.vue'
import PasswordChangeView from '@/views/auth/PasswordChange.vue'
import PasswordChangeCompleteView from '@/views/auth/PasswordChangeComplete.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/find-password',
      name: 'PasswordFind',
      component: PasswordFindView
    },
    {
      path: '/find-password/fail',
      name: 'PasswordFindFail',
      component: PasswordFindFailView
    },
    {
      path: '/password/change',
      name: 'PasswordChange',
      component: PasswordChangeView
    },
    {
      path: '/password/change-complete',
      name: 'PasswordChangeComplete',
      component: PasswordChangeCompleteView
    },
  ]
})

export default router
