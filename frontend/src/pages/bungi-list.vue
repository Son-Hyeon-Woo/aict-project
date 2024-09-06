<script setup>
import { useAxiosStore } from '@/stores/axios'

const axiosStore = useAxiosStore()
const axios = axiosStore.getAxiosInstance()

import PageTitle from '@/components/PageTitle.vue'
import SearchBar from '@/components/SearchBar.vue'
import AgGridTable from '@/components/agGridTable.vue'

const selectedBonbu = ref('전체')
const selectedCenter = ref('전체')
const selectedGuksaName = ref('')

const rowData = ref([])

const colDefs = ref([
  { headerName: '우선순위', field: 'priority', hide: true },
  {
    headerName: '그룹자산코드',
    field: 'group_asset_code',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '그룹자산명',
    field: 'group_asset_name',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '시설명',
    field: 'facility_name',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '광역본부',
    field: 'nw_org1',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '운용센터',
    field: 'unyong_center',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '시설유형 (과기부)',
    field: 'facility_type',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '중요통신등급',
    field: 'important_tongsin_grade',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '네트ID',
    field: 'management_number',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '국사유형',
    field: 'guksa_type',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  { headerName: '주소', field: 'address', headerClass: 'font-size-9 font-weight-bold', cellStyle: { 'text-align': 'center' } },
  {
    headerName: '소유회사',
    field: 'owning_company',
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  { headerName: '위치', field: 'location', headerClass: 'font-size-9 font-weight-bold', cellStyle: { 'text-align': 'center' } },
  {
    headerName: '준공년도',
    field: 'completion_year',
    valueFormatter: params => `${params.value}년`,
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '노후도',
    field: 'nohudo',
    valueFormatter: params => `${params.value}년 경과`,
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
  {
    headerName: '연면적',
    field: 'total_floor_area',
    valueFormatter: params => `${params.value.toLocaleString()} m²`,
    headerClass: 'font-size-9 font-weight-bold',
    cellStyle: { 'text-align': 'center' },
  },
])

const fetchData = async () => {
  try {
    const bodyData = {
      bonbu: selectedBonbu.value,
      center: selectedCenter.value,
      name: selectedGuksaName.value,
    }

    const response = await axios.post('bungi-list.php', bodyData)

    rowData.value = response.data.data
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

const router = useRouter()

const handleCellDoubleClick = event => {
  router.push(`/bungi-list/${event.node.data.group_asset_code}`)
  // window.open(`/guksa/bungi-list/${event.node.data.group_asset_code}`, '_blank', 'noopener,noreferrer,width=1620,height=1000') // 팝업 할 떄 주석 풀어주기
}
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
              title="분기국사 현황"
              icon="hugeicons:menu-square"
              font-size="1.2rem"
              font-weight="500"
            />
          </VCol>
          <!-- <VSpacer /> -->
          <VCol
            cols="12"
            lg="8"
          >
            <SearchBar
              v-model:selectedBonbu="selectedBonbu"
              v-model:selectedCenter="selectedCenter"
              v-model:searchQuery="selectedGuksaName"
              search-label="국사명"
              @search="fetchData"
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
