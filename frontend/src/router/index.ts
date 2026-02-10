import { createRouter, createWebHistory } from 'vue-router'
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Main',
      meta: { requiresAuth: false },
      component: () => import('@/pages/MainPage.vue')
    },
    {
      path: '/parties/:partyId',
      name: 'Party',
      meta: { requiresAuth: false },
      component: () => import('@/pages/PartyPage.vue')
    },
    {
      path: '/signIn',
      name: 'SignIn',
      meta: { requiresAuth: false },
      component: () => import('@/pages/SignInPage.vue')
    },
    {
      path: '/signUp',
      name: 'SignUp',
      meta: { requiresAuth: false },
      component: () => import('@/pages/SignUpPage.vue')
    },
    {
      path: '/profile',
      name: 'Profile',
      meta: { requiresAuth: true },
      component: () => import('@/pages/UserPage.vue')
    },
    {
      path: '/profile/parties',
      name: 'ProfileParties',
      meta: { requiresAuth: true },
      component: () => import('@/pages/UserPartiesPage.vue')
    },
    {
      path: '/profile/statistics',
      name: 'ProfileStatistics',
      meta: { requiresAuth: true },
      component: () => import('@/pages/UserStatisticsPage.vue')
    }
  ],
})

router.beforeEach(async (to) => {
  const authStore = useAuthStore()
  const { isLoggedIn, user } = storeToRefs(authStore)

  if (isLoggedIn.value && !user.value) {
    await authStore.loadProfile();
  }

  if (to.meta.requiresAuth && !isLoggedIn.value) {
    return '/'
  }
})

export default router