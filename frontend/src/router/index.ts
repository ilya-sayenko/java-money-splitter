import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/parties/:partyId',
      component: () => import('@/pages/PartyPage.vue')
    }
  ],
})

export default router