<template>
  <VRow no-gutters>
    <VCol
      cols="6"
      class="d-flex align-center justify-center"
    >
      <VueApexCharts
        type="donut"
        width="100%"
        :options="chartOptions1"
        :series="series1"
      />
    </VCol>
    <VCol
      cols="1"
      class="d-flex align-center justify-center pa-0"
    >
      <TripleArrows style="width: 100px; height: 100px" />
    </VCol>
    <VCol
      cols="5"
      class="d-flex align-center justify-center"
    >
      <VueApexCharts
        type="donut"
        width="100%"
        :options="chartOptions2"
        :series="series2"
      />
    </VCol>
  </VRow>
</template>

<script setup>
import { useAxiosStore } from '@/stores/axios'

import TripleArrows from '@/components/TripleArrow.vue'
import VueApexCharts from 'vue3-apexcharts'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()
let intervalId = null

const todayStats = ref({
  total: 0,
  detected: 0,
  blocked: 0,
})

const series1 = computed(() => {
  return [todayStats.value.detected, todayStats.value.total - todayStats.value.detected]
})

const chartOptions1 = ref({
  chart: {
    type: 'donut',
  },
  dataLabels: {
    enabled: false,
  },
  legend: {
    show: false,
  },
  plotOptions: {
    pie: {
      startAngle: 0,
      endAngle: 360,
      expandOnClick: true,
      offsetX: 0,
      offsetY: 0,
      customScale: 1,
      donut: {
        labels: {
          show: true,

          total: {
            show: true,
            showAlways: true,
            label: '탐지',
            fontWeight: 600,
            fontFamily: 'Pretendard Variable',
            fontSize: '1.2rem',
          },
          value: {
            show: true,
            fontWeight: 600,
            fontFamily: 'Pretendard Variable',
            fontSize: '1rem',
            offsetY: 0,
          },
        },
      },
    },
  },
  labels: ['탐지', ''],
  colors: ['#70dc61', '#00000020'],
  responsive: [
    {
      breakpoint: 480,
      options: {
        chart: {
          width: 200,
        },
      },
    },
  ],
})

const series2 = computed(() => {
  return [todayStats.value.blocked, todayStats.value.detected - todayStats.value.blocked]
})

const chartOptions2 = ref({
  chart: {
    type: 'donut',
  },
  dataLabels: {
    enabled: false,
  },
  legend: {
    show: false,
  },
  plotOptions: {
    pie: {
      startAngle: 0,
      endAngle: 360,
      expandOnClick: true,
      offsetX: 0,
      offsetY: 0,
      donut: {
        labels: {
          show: true,

          total: {
            show: true,
            showAlways: true,
            label: '차단',
            fontWeight: 600,
            fontFamily: 'Pretendard Variable',
            fontSize: '1rem',
          },
          value: {
            show: true,
            fontWeight: 600,
            fontFamily: 'Pretendard Variable',
            fontSize: '1rem',
            offsetY: 0,
          },
        },
      },
    },
  },
  labels: ['탐지', ''],
  colors: ['#C13030', '#00000020'],
  responsive: [
    {
      breakpoint: 480,
      options: {
        chart: {
          width: 200,
        },
      },
    },
  ],
})

const fetchTodayDetection = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/today-detected-emails')

    // {totalEmails: 60, blockedHighRiskEmails: 4, highRiskEmails: 12}
    todayStats.value.total = response.data.data.totalEmails
    todayStats.value.detected = response.data.data.highRiskEmails
    todayStats.value.blocked = response.data.data.blockedHighRiskEmails
    console.log(todayStats.value)
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

onMounted(() => {
  fetchTodayDetection()
  intervalId = setInterval(fetchTodayDetection, 5000) // 1분마다 폴링
})

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId) // 컴포넌트 언마운트 시 폴링 중지
})
</script>

<style scoped lang="scss">
// .ag-theme-quartz {
//   font-family: 'Pretendard Variable';
// }
</style>
