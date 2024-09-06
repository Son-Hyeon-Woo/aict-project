<script setup>
import { useAxiosStore } from '@/stores/axios'
import CategoryPieChart from '@/views/CategoryPieChart.vue'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()
const router = useRouter()

onMounted(() => {})
</script>

<template>
  <VRow>
    <VCol cols="8">
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
            <VLabel>위협 메일 시간대별 통계</VLabel>
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
