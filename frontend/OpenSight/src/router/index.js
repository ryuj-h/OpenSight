import { createRouter, createWebHistory } from 'vue-router';
import IntroView from '@/views/intro/Intro.vue';
import LoginView from '@/views/auth/Login.vue';
import MainView from '@/views/dashboard/Main.vue';
import RegisterView from '@/views/auth/Register.vue'
import RegisterCompleteView from '@/views/auth/RegisterComplete.vue'
import FindEmail from '@/views/auth/FindEmail.vue'
import FindEmailComplete from '@/views/auth/FindEmailComplete.vue'
import FindEmailFail from '@/views/auth/FindEmailFail.vue'
import EditProfileView from '@/views/profile/EditProfile.vue'
import EditProfileCompleteView from '@/views/profile/EditProfileComplete.vue'
import PasswordFindView from '@/views/auth/PasswordFind.vue'
import PasswordFindFailView from '@/views/auth/PasswordFindFail.vue'
import PasswordChangeView from '@/views/auth/PasswordChange.vue'
import PasswordChangeCompleteView from '@/views/auth/PasswordChangeComplete.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/main',
      name: 'Main',
      component: MainView
    },
    {
      path: '/',
      name: 'Intro',
      component: IntroView
    },
    {
      path: '/register',
      name: 'Reigster',
      component: RegisterView
    },
    {
      path: '/register/complete',
      name: 'RegisterComplete',
      component: RegisterCompleteView
    },
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
    },
    {
      path: '/profile/edit',
      name: 'EditProfile',
      component: EditProfileView
    },
    {
      path: '/profile/edit-complete',
      name: 'EditProfileComplete',
      component: EditProfileCompleteView
    },
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
