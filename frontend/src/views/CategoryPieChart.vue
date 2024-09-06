<template>
  <VRow>
    <VCol
      cols="12"
      class="pt-0"
    >
      <VueApexCharts
        type="pie"
        width="380"
        :options="chartOptions"
        :series="series"
      />
    </VCol>
  </VRow>
</template>

<script setup>
import VueApexCharts from 'vue3-apexcharts'

const series = ref([44, 55, 13, 43, 22])

const chartOptions = ref({
  chart: {
    width: 380,
    type: 'pie',
  },
  labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
  responsive: [
    {
      breakpoint: 480,
      options: {
        chart: {
          width: 200,
        },
        legend: {
          position: 'bottom',
        },
      },
    },
  ],
})
</script>

<style scoped lang="scss">
.ag-theme-quartz {
  font-family: 'Pretendard Variable';
}
</style>
