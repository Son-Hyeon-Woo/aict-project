<template>
  <!-- 검색 필터 -->
  <VRow
    align-content="center"
    justify="center"
    justify-lg="end"
  >
    <!-- 지역본부 -->
    <VCol
      v-if="!hideBonbu"
      cols="12"
      md="3"
      lg="2"
    >
      <BonbuSelect v-model="localSelectedBonbu" />
    </VCol>

    <!-- 운용센터 -->
    <VCol
      v-if="!hideCenter"
      cols="12"
      md="3"
    >
      <CenterSelect
        v-model="localSelectedCenter"
        :bonbu="localSelectedBonbu"
      />
    </VCol>

    <!-- 검색 필드 -->
    <VCol
      v-if="!hideSearchField"
      cols="12"
      md="3"
      lg="2"
    >
      <VTextField
        v-model="localSearchQuery"
        :label="searchLabel"
        variant="underlined"
        density="compact"
        class="map-filter"
        hide-details
        clearable
        single-line
        @keyup.enter="onSearch"
      />
    </VCol>

    <!-- 검색 버튼 -->
    <VCol
      cols="12"
      md="3"
      lg="2"
      class="d-flex justify-center justify-md-end align-center"
    >
      <VBtn
        id="search-btn"
        color="primary"
        variant="tonal"
        class="h-100 w-100"
        @click="onSearch"
      >
        검색
      </VBtn>
    </VCol>
  </VRow>
</template>

<script setup>
import BonbuSelect from '@/components/BonbuSelect.vue';
import CenterSelect from '@/components/CenterSelect.vue';

const props = defineProps({
  selectedBonbu: {
    type: String,
    default: '',
  },
  selectedCenter: {
    type: String,
    default: '',
  },
  searchQuery: {
    type: String,
    default: '',
  },
  searchLabel: {
    type: String,
    default: '검색',
  },
  hideBonbu: {
    type: Boolean,
    default: false,
  },
  hideCenter: {
    type: Boolean,
    default: false,
  },
  hideSearchField: {
    type: Boolean,
    default: false,
  },
})

const emits = defineEmits(['update:selectedBonbu', 'update:selectedCenter', 'update:searchQuery', 'search'])

const localSelectedBonbu = ref(props.selectedBonbu)
const localSelectedCenter = ref(props.selectedCenter)
const localSearchQuery = ref(props.searchQuery)

watch(localSelectedBonbu, newVal => emits('update:selectedBonbu', newVal))
watch(localSelectedCenter, newVal => emits('update:selectedCenter', newVal))
watch(localSearchQuery, newVal => emits('update:searchQuery', newVal))

const onSearch = () => {
  emits('search')
}
</script>

<style lang="scss" scoped>
#search-btn {
  block-size: 2rem !important;
  min-inline-size: 6rem !important;
}

.map-filter {
  color: rgb(var(--v-theme-grey-900));
  font-weight: 600;
}
</style>
