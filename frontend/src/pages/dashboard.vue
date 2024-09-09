<script setup>
import { useAxiosStore } from '@/stores/axios'
import { useUpdateTimeStore } from '@/stores/useUpdateTimeStore'
import DetectionCategoryChart from '@/views/DetectionCategoryChart.vue'
import RiskEmailList from '@/views/RiskEmailList.vue'
import TimeBasedDetection from '@/views/TimeBasedDetection.vue'
import TodayDetection from '@/views/TodayDetection.vue'
import WeeklyDetection from '@/views/WeeklyDetection.vue'

const axiosStore = useAxiosStore()

// const axios = axiosStore.getAxiosInstance()
// const router = useRouter()

const updateTimeStore = useUpdateTimeStore()

onMounted(() => {})
</script>

<template>
  <VRow>
    <VCol
      sm="12"
      md="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1 d-flex flex-column">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일 탐지/차단 현황</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <TodayDetection />
        </div>
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 0.9rem; font-weight: 700; color: #00000075">
            마지막 업데이트 시간: {{ updateTimeStore.todayDetection }}
          </VLabel>
        </div>
      </VSheet>
    </VCol>
    <VCol
      sm="12"
      md="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일/전일 위협메일 탐지 추이</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <TimeBasedDetection />
        </div>
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 0.9rem; font-weight: 700; color: #00000075">
            마지막 업데이트 시간: {{ updateTimeStore.timeBasedDetection }}
          </VLabel>
        </div>
      </VSheet>
    </VCol>
    <VCol
      sm="12"
      md="4"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">금일 위협메일 유형별 통계</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <DetectionCategoryChart />
        </div>
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 0.9rem; font-weight: 700; color: #00000075">
            마지막 업데이트 시간: {{ updateTimeStore.detectionCategory }}
          </VLabel>
        </div>
      </VSheet>
    </VCol>
  </VRow>
  <VRow>
    <VCol
      sm="12"
      md="7"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1 d-flex flex-column">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">실시간 차단 메일 리스트</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <RiskEmailList />
        </div>
      </VSheet>
    </VCol>
    <VCol
      sm="12"
      md="5"
      class="d-flex flex-column"
    >
      <VSheet class="pa-4 elevation-12 rounded flex-grow-1 d-flex flex-column">
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 1.2rem; font-weight: 700; color: #00000095">최근 7일 탐지 추이</VLabel>
        </div>
        <div class="d-flex align-center justify-center flex-grow-1">
          <WeeklyDetection />
        </div>
        <div class="d-flex align-center justify-center">
          <VLabel style="font-size: 0.9rem; font-weight: 700; color: #00000075">
            마지막 업데이트 시간: {{ updateTimeStore.weeklyDetection }}
          </VLabel>
        </div>
      </VSheet>
    </VCol>
  </VRow>
</template>

<style lang="scss" scoped>
// pass
</style>
