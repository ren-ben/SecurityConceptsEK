import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import HomeView from '@/views/HomeView.vue'
import CommunityView from '@/views/CommunityView.vue'
import OperationsView from '@/views/OperationsView.vue'
import PublicView from '@/views/PublicView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/public',
      name: 'public',
      component: PublicView
    },
    {
      path: '/community',
      name: 'community',
      component: CommunityView,
      meta: { requiresAuth: true }
    },
    {
      path: '/operations',
      name: 'operations',
      component: OperationsView,
      meta: { requiresAuth: true, requiresOps: true }
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  // Fetch user wenn noch nicht geladen
  if (authStore.user === null && !authStore.loading) {
    await authStore.fetchUser()
  }

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'home' })
  } else if (to.meta.requiresOps && !authStore.isOps) {
    next({ name: 'home' })
  } else {
    next()
  }
})

export default router
