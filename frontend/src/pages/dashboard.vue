<script setup>
import { useAxiosStore } from '@/stores/axios'
import TimeBasedDetection from '@/views/TimeBasedDetection.vue'
import TodayDetection from '@/views/TodayDetection.vue'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()
const router = useRouter()

onMounted(() => {})
</script>

<template>
  <VRow>
    <VCol
      cols="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1 d-flex flex-column">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일 탐지/차단 현황</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <TodayDetection />
        </div>
      </VSheet>
    </VCol>
    <VCol
      cols="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일/전일 위협메일 탐지 추이</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <TimeBasedDetection />
        </div>
      </VSheet>
    </VCol>
    <VCol
      cols="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일 위협메일 유형별 통계</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <TimeBasedDetection />
        </div>
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
