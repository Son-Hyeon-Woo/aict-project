import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/dashboard' },
    { path: '/index.html', redirect: '/dashboard' },
    {
      path: '/',
      component: () => import('../layouts/default.vue'),
      name: 'default',
      children: [
        {
          path: 'dashboard',
          component: () => import('../pages/dashboard.vue'),
          name: 'dashboard',
        },
        {
          path: 'mail-list',
          component: () => import('../pages/mail-list.vue'),
          name: 'mail-list',
        },
      ],
    },
    {
      path: '/',
      component: () => import('../layouts/blank.vue'),
      children: [
        // login.vue 경로 제거
        // {
        //   path: 'login',
        //   component: () => import('../pages/login.vue'),
        //   name: 'login',
        // },
        {
          path: '/:pathMatch(.*)*',
          component: () => import('../pages/[...all].vue'),
          name: 'not-found',
        },
      ],
    },
  ],
})

export default router
