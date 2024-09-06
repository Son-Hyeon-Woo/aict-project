<script setup>
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'
import VerticalNavLinkGroup from '@layouts/components/VerticalNavLinkGroup.vue'

// Components
import Footer from '@/layouts/components/Footer.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const page_name = computed(() => route.name)

const categoryName = ref('')

provide('categoryName', categoryName)
</script>

<template>
  <VerticalNavLayout>
    <!-- 👉 navbar -->
    <!-- ✨ 상단바 -->
    <template #navbar="{ toggleVerticalOverlayNavActive }">
      <div class="d-flex h-100 align-center">
        <!-- 👉 Vertical nav toggle in overlay mode -->
        <IconBtn
          class="ms-n3 d-lg-none"
          @click="toggleVerticalOverlayNavActive(true)"
        >
          <VIcon icon="bx-menu" />
        </IconBtn>

        <VSpacer />

        <NavbarThemeSwitcher class="me-2" />

        <UserProfile />
      </div>
    </template>
    <!-- ✨ 사이드바 -->
    <template #vertical-nav-content>
      <VerticalNavLinkGroup
        :item="{
          title: '이메일 위협 정보',
          icon: 'fluent:square-12-filled',
          items: [
            { title: '위협 현황', to: '/dashboard' },
            { title: '위협 메일 리스트', to: '/mail-list' },
          ],
        }"
      />

      <VerticalNavLinkGroup
        :item="{
          title: '설정',
          icon: 'ep:setting',
          items: [{ title: '관리자 페이지', to: '/temp', disable: 'true' }],
        }"
      />
    </template>

    <!-- 👉 Pages -->
    <slot />

    <!-- 👉 Footer -->
    <template #footer>
      <Footer />
    </template>
  </VerticalNavLayout>
</template>
