<template>
  <VRow no-gutters>
    <VCol
      cols="12"
      class="pt-0"
    >
      <VueApexCharts
        type="line"
        height="280"
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

const updateTimeStore = useUpdateTimeStore()

let intervalId = null

const series = ref([
  {
    data: [0, 0, 0, 0, 0, 0, 0],
  },
])

const chartOptions = ref({
  chart: {
    type: 'line',
    height: 200,
    zoom: {
      enabled: false,
    },
  },
  dataLabels: {
    enabled: true,
  },
  stroke: {
    curve: 'straight',
  },
  grid: {
    row: {
      colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.5,
    },
  },
  xaxis: {
    categories: [],
  },
  legend: {
    show: false,
  },
})

const fetchWeeklyDetection = async () => {
  try {
    const response = await axios.get('count-by-risk-detail-last-7-days')

    // 날짜별로 데이터 정렬
    const sortedData = Object.entries(response.data.data.emailCountByRiskDetail)
      .sort(([dateA], [dateB]) => new Date(dateA) - new Date(dateB))
      .reduce((acc, [date, count]) => {
        acc[date] = count

        return acc
      }, {})

    // 정렬된 데이터로 series와 categories 업데이트
    series.value[0].data = Object.values(sortedData)
    chartOptions.value = {
      ...chartOptions.value,
      xaxis: {
        ...chartOptions.value.xaxis,
        categories: Object.keys(sortedData),
      },
    }

    // 마지막 업데이트 시간 설정
    updateTimeStore.setWeeklyDetectionUpdateTime(response.data.data.lastUpdateTime)
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

onMounted(() => {
  fetchWeeklyDetection()
  intervalId = setInterval(fetchWeeklyDetection, 5000) // 10초마다 폴링
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
