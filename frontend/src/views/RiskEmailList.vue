<script setup>
import { useAxiosStore } from '@/stores/axios'

let intervalId = null
const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()

import AgGridTable from '@/components/agGridTable.vue'

const rowData = ref([])

const colDefs = ref([
  {
    headerName: '제목',
    field: 'title',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '위험도',
    field: 'riskLevel',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '위험유형',
    field: 'riskDetail',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '차단일자',
    field: 'detectionDate',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
])

const fetchData = async () => {
  try {
    const response = await axios.get('last-10-blocked-emails')

    rowData.value = response.data.data.emails
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

onMounted(() => {
  fetchData()
  intervalId = setInterval(fetchData, 2000) // 10초마다 폴링
})

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId) // 컴포넌트 언마운트 시 폴링 중지
})
</script>

<template>
  <VRow>
    <VCol cols="12">
      <AgGridTable
        :row-data="rowData"
        :col-defs="colDefs"
        height="calc(100vh - 39rem)"
        :pagination-page-size="5"
        :pagination-page-size-selector="[5]"
        loading-message="데이터 로딩 중..."
        :fetch-data="fetchData"
      />
    </VCol>
  </VRow>
</template>
