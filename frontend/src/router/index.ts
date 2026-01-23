import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/pages/MainPage.vue')
    },
    {
      path: '/parties',
      component: () => import('@/pages/MainPage.vue')
    },
    {
      path: '/parties/:partyId',
      component: () => import('@/pages/PartyPage.vue')
    },
    {
      path: '/auth',
      component: () => import('@/pages/AuthPage.vue')
    },
    {
      path: '/profile',
      component: () => import('@/pages/ProfilePage.vue')
    }
  ],
})

export default router