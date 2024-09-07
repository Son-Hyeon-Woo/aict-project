<script setup>
import { useAxiosStore } from '@/stores/axios'
import CategoryPieChart from '@/views/CategoryPieChart.vue'
import TodayDetection from '@/views/TodayDetection.vue'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()
const router = useRouter()

onMounted(() => {})
</script>

<template>
  <VRow>
    <VCol cols="4">
      <VSheet
        class="pa-4"
        elevation="12"
        rounded
      >
        <VRow>
          <VCol
            cols="12"
            class="d-flex align-center justify-center"
          >
            <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일 탐지/차단 현황</VLabel>
          </VCol>
          <VCol class="d-flex align-center justify-center">
            <TodayDetection />
          </VCol>
        </VRow>
      </VSheet>
    </VCol>
    <VCol cols="4">
      <VSheet
        class="pa-4"
        elevation="12"
        rounded
      >
        <VRow>
          <VCol>
            <VIcon
              icon="hugeicons:menu-square"
              class="me-2"
            />
            <VLabel>위협 메일 위험도별 통계</VLabel>
            <CategoryPieChart />
          </VCol>
        </VRow>
      </VSheet>
    </VCol>
    <VCol cols="4">
      <VSheet
        class="pa-4"
        elevation="12"
        rounded
      >
        <VRow>
          <VCol>
            <VIcon
              icon="hugeicons:menu-square"
              class="me-2"
            />
            <VLabel>위협 메일 위험도별 통계</VLabel>
            <CategoryPieChart />
          </VCol>
        </VRow>
      </VSheet>
    </VCol>
  </VRow>
  <VRow>
    <VCol>
      <VSheet
        class="pa-4"
        elevation="12"
        rounded
      >
        <VRow>
          <VCol>
            <VIcon
              icon="hugeicons:menu-square"
              class="me-2"
            />
            <VLabel>위협 메일 유형별 통계</VLabel>
          </VCol>
        </VRow>
      </VSheet>
    </VCol>
  </VRow>
</template>

<style lang="scss" scoped>
// pass
</style>
