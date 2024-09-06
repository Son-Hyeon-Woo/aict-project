import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import { defineStore } from 'pinia'

export const useAxiosStore = defineStore('axiosStore', {
  state: () => ({
    axiosInstance: null,
  }),

  actions: {
    initializeAxios() {
      // Axios 인스턴스 생성
      this.axiosInstance = axios.create({
        baseURL: '',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer yourTokenHere',
        },
      })

      // 요청 인터셉터 추가
      this.axiosInstance.interceptors.request.use(
        config => {
          // 요청 전 처리 작업 수행

          return config
        },
        error => {
          // 요청 오류 처리
          return Promise.reject(error)
        },
      )

      // 응답 인터셉터 추가
      this.axiosInstance.interceptors.response.use(
        response => {
          // 응답 데이터 처리
          console.log(response.data.message)

          return response
        },
        error => {
          // 응답 오류 처리
          return Promise.reject(error)
        },
      )

      // Mock Adapter 설정
      const mock = new MockAdapter(this.axiosInstance)

      mock.onAny().passThrough()
    },

    getAxiosInstance() {
      return this.axiosInstance
    },
  },
})
