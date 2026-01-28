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
      component: () => import('@/pages/UserPage.vue')
    },
    {
      path: '/profile/parties',
      component: () => import('@/pages/UserPartiesPage.vue')
    },
    {
      path: '/profile/statistics',
      component: () => import('@/pages/UserStatisticsPage.vue')
    }
  ],
})

export default router