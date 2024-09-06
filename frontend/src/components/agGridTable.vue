<template>
  <VRow>
    <VCol
      cols="12"
      class="pt-0"
    >
      <AgGridVue
        :auto-size-strategy="{ type: 'fitCellContents' }"
        :auto-size-padding="10"
        :row-data="rowData"
        :column-defs="colDefs"
        :header-height="headerHeight"
        :style="{ height: height || 'calc(100vh - 15rem)' }"
        class="ag-theme-quartz mt-4"
        row-selection="single"
        pagination="true"
        :pagination-page-size="paginationPageSize"
        :pagination-page-size-selector="paginationPageSizeSelector"
        :loading-overlay-component="loadingOverlayComponent"
        :loading-overlay-component-params="loadingOverlayComponentParams"
        v-on="getAgGridEvents()"
      />
    </VCol>
  </VRow>
</template>

<script setup>
import CustomLoadingOverlay from '@/components/agGridLoadingOverlay.vue'
import 'ag-grid-community/styles/ag-grid.css'
import 'ag-grid-community/styles/ag-theme-quartz.css'
import { AgGridVue } from 'ag-grid-vue3'

const props = defineProps({
  rowData: {
    type: Array,
    default: () => [],
  },
  colDefs: {
    type: Array,
    default: () => [],
  },
  headerHeight: {
    type: Number,
    default: 45,
  },
  paginationPageSize: {
    type: Number,
    default: 15,
  },
  paginationPageSizeSelector: {
    type: Array,
    default: () => [15, 20, 30, 40, 50],
  },
  loadingMessage: {
    type: String,
    default: '로딩 중...',
  },
  fetchData: {
    type: Function,
    required: true,
  },
  onCellDoubleClick: {
    type: Function,
    default: null,
  },
  height: {
    // 추가된 부분
    type: String,
    default: null,
  },
})

const emits = defineEmits(['gridReady'])

const onGridReady = params => {
  params.api.sizeColumnsToFit()
  params.api.showLoadingOverlay()
  emits('gridReady', params)
}

const celldouble = event => {
  if (props.onCellDoubleClick) {
    props.onCellDoubleClick(event)
  }
}

const getAgGridEvents = () => {
  const events = {
    gridReady: onGridReady,
  }

  if (props.onCellDoubleClick) {
    events['cellDoubleClicked'] = celldouble
  }

  return events
}

onMounted(() => {
  props.fetchData()
})

const loadingOverlayComponent = ref(CustomLoadingOverlay)
const loadingOverlayComponentParams = ref({ loadingMessage: props.loadingMessage })
</script>

<style scoped lang="scss">
.ag-theme-quartz {
  font-family: 'Pretendard Variable';
}

:deep(.font-size-10) {
  font-size: 1rem;
}

:deep(.font-size-9) {
  font-size: 0.9rem;
}

:deep(.bg-normal) {
  background-color: rgba(70, 70, 70, 10%);
  border-block-end: 0.7px solid #00000020; // 테이블 아래 border의 색을 진하게 설정
}

:deep(.normal-header) {
  background-color: rgba(100, 100, 100, 10%);
  font-size: 1rem;
  font-size: 0.9rem;
}

:deep(.ag-header-cell-label) {
  justify-content: center;

  .ag-header-cell-text {
    white-space: pre-wrap !important;
  }
}

// 셀 내용 줄바꿈
:deep(.ag-cell) {
  white-space: pre-wrap !important;
}

@mixin hover-darken($color) {
  background-color: $color;

  &:hover {
    background-color: darken($color, 3%) !important;
  }
}

:deep(.bg-skyblue) {
  @include hover-darken(rgb(196, 231, 255));
}

:deep(.bg-lightok) {
  @include hover-darken(rgb(196, 248, 255));
}

:deep(.bg-lightred) {
  @include hover-darken(rgb(255, 230, 230));
}

:deep(.bg-lightgreen) {
  @include hover-darken(rgb(238, 255, 234));
}

:deep(.bg-lightyellow) {
  @include hover-darken(rgb(255, 253, 218));
}

:deep(.grid-cand-two) {
  background-color: rgb(195, 230, 253);
  font-weight: 700;
}
</style>
