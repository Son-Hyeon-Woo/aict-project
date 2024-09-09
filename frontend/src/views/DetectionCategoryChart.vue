<template>
  <VRow>
    <VCol
      cols="12"
      class="pt-0"
    >
      <VueApexCharts
        type="bar"
        :options="chartOptions"
        :series="series"
      />
    </VCol>
  </VRow>
</template>

<script setup>
import { useAxiosStore } from '@/stores/axios'
import { useUpdateTimeStore } from '@/stores/useUpdateTimeStore'

import VueApexCharts from 'vue3-apexcharts'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()
let intervalId = null

const updateTimeStore = useUpdateTimeStore()

const series = ref([
  {
    data: [0, 0, 0, 0, 0],
  },
])

const chartOptions = ref({
  chart: {
    height: 350,
    type: 'bar',
    events: {
      click: function (chart, w, e) {
        // console.log(chart, w, e)
      },
    },
  },
  colors: ['#FF6B6B', '#4ECDC4', '#45B7D1', '#FFA07A', '#98D8C8'],
  plotOptions: {
    bar: {
      columnWidth: '45%',
      distributed: true,
    },
  },
  dataLabels: {
    enabled: false,
  },
  legend: {
    show: false,
  },
  xaxis: {
    categories: [
      ['John', 'Doe'],
      ['Joe', 'Smith'],
      ['Jake', 'Williams'],

      ['Peter', 'Brown'],
      ['Mary', 'Evans'],
    ],
    labels: {
      style: {
        colors: ['#FF6B6B', '#4ECDC4', '#45B7D1', '#FFA07A', '#98D8C8'],
        fontSize: '12px',
      },
    },
  },
})

async function getEmailsCountByRiskLevel() {
  const response = await axios.get('count-by-risk-detail')

  // {멀웨어: 143, 랜섬웨어: 144, 스팸: 126, 피싱: 154}
  series.value[0].data = Object.values(response.data.data.emailCountByRiskDetail)

  chartOptions.value = {
    ...chartOptions.value,
    xaxis: {
      ...chartOptions.value.xaxis,
      categories: Object.keys(response.data.data.emailCountByRiskDetail),
    },
  }

  console.log(response.data.data)
  updateTimeStore.setDetectionCategoryUpdateTime(response.data.data.lastUpdateTime)
}

onMounted(() => {
  getEmailsCountByRiskLevel()
  intervalId = setInterval(getEmailsCountByRiskLevel, 10000) // 10초마다 폴링
})

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId) // 컴포넌트 언마운트 시 폴링 중지
})
</script>

<style scoped lang="scss">
.ag-theme-quartz {
  font-family: 'Pretendard Variable';
}
</style>
