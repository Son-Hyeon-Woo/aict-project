<template>
  <VRow>
    <VCol
      cols="12"
      class="pt-0"
    >
      <VueApexCharts
        type="area"
        :options="chartOptions"
        :series="series"
      />
    </VCol>
  </VRow>
</template>

<script setup>
import { useAxiosStore } from '@/stores/axios'
import { onBeforeMount } from 'vue'
import VueApexCharts from 'vue3-apexcharts'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()

const series = ref([
  {
    name: '금일',
    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  },
  {
    name: '전일',
    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
  },
])

const chartOptions = ref({
  chart: {
    height: 350,
    type: 'area',
    toolbar: {
      show: false,
    },
  },
  dataLabels: {
    enabled: false,
  },
  stroke: {
    curve: 'smooth',
  },
  xaxis: {
    type: 'time',
    categories: ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'],
  },
  tooltip: {
    x: {
      format: 'HH:mm',
    },
  },
})

const fetchTimeBasedDetection = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/hourly-detected-emails')

    series.value[0].data = response.data.data.series[0].data
    series.value[1].data = response.data.data.series[1].data

    const extractedCategories = response.data.data.categories.map(category => {
      return category.split(' ')[1]
    })

    // chartOptions 객체를 새 객체로 할당
    chartOptions.value = {
      ...chartOptions.value,
      xaxis: {
        ...chartOptions.value.xaxis,
        categories: extractedCategories,
      },
    }

    console.log(response)
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

onBeforeMount(() => {
  fetchTimeBasedDetection()
})
</script>

<style scoped lang="scss">
.ag-theme-quartz {
  font-family: 'Pretendard Variable';
}
</style>
