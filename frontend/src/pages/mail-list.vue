<script setup>
import { useAxiosStore } from '@/stores/axios'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()

import PageTitle from '@/components/PageTitle.vue'
import AgGridTable from '@/components/agGridTable.vue'

const rowData = ref([])

const colDefs = ref([
  {
    headerName: '메일번호',
    field: 'emailNo',
    headerClass: 'font-size-9 font-weight-bold bg-skyblue',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '제목',
    field: 'subject',
    headerClass: 'font-size-9 font-weight-bold bg-lightgreen',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '보낸사람',
    field: 'sender',
    headerClass: 'font-size-9 font-weight-bold bg-lightgreen',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '받는사람',
    field: 'recipients',
    headerClass: 'font-size-9 font-weight-bold bg-lightgreen',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '첨부파일',
    field: 'attachments',
    headerClass: 'font-size-9 font-weight-bold bg-lightgreen',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '수신일자',
    field: 'receivedDate',
    headerClass: 'font-size-9 font-weight-bold bg-lightgreen',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '처리상태',
    field: 'processResult',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '위험수준',
    field: 'riskLevel',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '위험내용',
    field: 'riskDetail',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '탐지일자',
    field: 'detectionDate',
    headerClass: 'font-size-9 font-weight-bold bg-lightred',
    cellStyle: { 'text-align': 'center' },
  },
])

const fetchData = async () => {
  try {
    const bodyData = {}

    const response = await axios.get('recent-emails', bodyData)

    rowData.value = response.data.data.emails
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

let intervalId = null

onMounted(() => {
  fetchData()
  intervalId = setInterval(fetchData, 1000) // 10초마다 폴링
})

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId) // 컴포넌트 언마운트 시 폴링 중지
})

const router = useRouter()

const handleCellDoubleClick = event => {}
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      class="px-2"
    >
      <VSheet
        class="pa-4"
        elevation="12"
        rounded
      >
        <VRow>
          <VCol
            cols="12"
            lg="4"
            class="pb-0 d-flex align-start justify-center justify-lg-start"
          >
            <PageTitle
              title="실시간 메일 처리 목록"
              icon="hugeicons:menu-square"
              font-size="1.2rem"
              font-weight="500"
            />
          </VCol>
        </VRow>
        <!-- 지도 위 행 -->
        <AgGridTable
          :row-data="rowData"
          :col-defs="colDefs"
          :pagination-page-size="15"
          :pagination-page-size-selector="[15, 20, 30, 40, 50]"
          loading-message="데이터 로딩 중..."
          :fetch-data="fetchData"
          :on-cell-double-click="handleCellDoubleClick"
        />
      </VSheet>
    </VCol>
  </VRow>
</template>

<style>
/* pass */
</style>
