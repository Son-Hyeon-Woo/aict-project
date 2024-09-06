<template>
  <VSelect
    v-model="localSelectedCenter"
    label="운용센터"
    class="map-filter"
    placeholder="센터를 선택하세요"
    :items="CenterItems"
    variant="underlined"
    density="compact"
  >
    <template #selection="{ item }">
      <label class="ellipsis">{{ item.title }}</label>
    </template>
  </VSelect>
</template>

<script setup>
  const props = defineProps({
    modelValue: String,
    bonbu: String,
  })

  const emit = defineEmits(['update:modelValue'])

  import { useAxiosStore } from '@/stores/axios'
  import { watch } from 'vue'

  const axiosStore = useAxiosStore()
  const axios = axiosStore.getAxiosInstance()
  const CenterItems = ref([])

  const fetchData = async selectedBonbu => {
    try {
      const bodyData = {
        requestType: 'center',
        bonbu: selectedBonbu,
      }

      const response = await axios.post('common/jojik.php', bodyData)

      CenterItems.value = response.data.data

      //* 첫 번째 아이템을 자동으로 선택
      localSelectedCenter.value = response.data.data[0]
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }

  //* 부모 컴포넌트로 받은 초기값을 여기서 저장하여 사용
  const localSelectedCenter = ref(props.modelValue)

  watch(localSelectedCenter, newValue => {
    emit('update:modelValue', newValue)
  })

  //* 부모컴포넌트의 bonbu 선택값이 바뀌면 자식컴포넌트의 CenterItems도 바뀔 수 있도록 함
  const bonbuRef = toRef(props, 'bonbu')

  onMounted(async () => {
    try {
      const bodyData = {
        requestType: 'center',
        bonbu: '전체',
      }

      const response = await axios.post('common/jojik.php', bodyData)

      CenterItems.value = response.data.data

      //* 첫 번째 아이템을 자동으로 선택
      localSelectedCenter.value = response.data.data[0]
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  })

  watch(bonbuRef, newValue => {
    fetchData(newValue)
  })
</script>

<style lang="scss" scoped>
  .map-filter {
    color: rgb(var(--v-theme-grey-900));
    font-weight: 600;
  }

  :deep(.v-label.v-field-label.v-field-label--floating) {
    color: rgb(var(--v-theme-grey-400));
  }

  .ellipsis {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
</style>
