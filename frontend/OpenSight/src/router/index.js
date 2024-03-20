import { createRouter, createWebHistory } from 'vue-router'
import EditProfileView from '@/views/profile/EditProfile.vue'
import EditProfileCompleteView from '@/views/profile/EditProfileComplete.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/profile/edit',
      name: 'EditProfile',
      component: EditProfileView
    },
    {
      path: '/profile/edit-complete',
      name: 'EditProfileComplete',
      component: EditProfileCompleteView
    }
  ]
})

export default router
