import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUpdateTimeStore = defineStore('updateTime', () => {
  const timeBasedDetection = ref(null)
  const todayDetection = ref(null)
  const detectionCategory = ref(null)

  const setTimeBasedDetectionUpdateTime = time => {
    timeBasedDetection.value = time
  }

  const setTodayDetectionUpdateTime = time => {
    todayDetection.value = time
  }

  const setDetectionCategoryUpdateTime = time => {
    detectionCategory.value = time
  }

  return {
    timeBasedDetection,
    todayDetection,
    detectionCategory,
    setTimeBasedDetectionUpdateTime,
    setTodayDetectionUpdateTime,
    setDetectionCategoryUpdateTime,
  }
})
