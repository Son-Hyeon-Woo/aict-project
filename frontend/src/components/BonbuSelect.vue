<template>
  <VSelect
    v-model="localSelectedBonbu"
    label="지역본부"
    class="map-filter"
    placeholder="본부를 선택하세요"
    :items="bonbuItems"
    variant="underlined"
    density="compact"
  >
    <template #selection="{ item }">
      <label class="ellipsis">{{ item.title }}</label>
    </template>
  </VSelect>
</template>

<script setup>
  import { useAxiosStore } from '@/stores/axios'

  const props = defineProps({
    modelValue: String,
  })

  //* 부모 컴포넌트에서 데이터 업데이트하는 이벤트
  const emit = defineEmits(['update:modelValue'])

  //* 내부에서 사용할 변수 정의
  const bonbuItems = ref([])
  const localSelectedBonbu = ref(props.modelValue)

  //* 자식 컴포넌트에서 데이터가 변경되면 부모 컴포넌트로 전달
  watch(localSelectedBonbu, newValue => {
    emit('update:modelValue', newValue)
  })

  const axiosStore = useAxiosStore()
  const axios = axiosStore.getAxiosInstance()

  //* 데이터(통신국사 현황) 가져오는 함수
  const fetchData = async () => {
    try {
      const bodyData = {
        requestType: 'bonbu',
      }

      const response = await axios.post('common/jojik.php', bodyData)

      bonbuItems.value = response.data.data

      //* 첫 번째 아이템을 자동으로 선택
      localSelectedBonbu.value = response.data.data[0]
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }

  onMounted(fetchData)
  watch(
    () => props.modelValue,
    newValue => {
      localSelectedBonbu.value = newValue
    },
  )
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
