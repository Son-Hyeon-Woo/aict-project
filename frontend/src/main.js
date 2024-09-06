import axios from 'axios'

// 세션 검증 함수
function checkSession() {
  return axios
    .get('https://mnow.kt.co.kr:8080/papi/guksa/check_session.php')
    .then(response => {
      if (!response.data.isValid) {
        window.location.href = 'https://mnow.kt.co.kr:8080/guksa/member/login.html'
        return false
      }
      return true
    })
    .catch(() => {
      window.location.href = 'https://mnow.kt.co.kr:8080/guksa/member/login.html'
      return false
    })
}

// 라우터 가드 설정
router.beforeEach(async (to, from, next) => {
  const isLocalHost = window.location.hostname === 'localhost' && window.location.port === '5173'

  if (isLocalHost) {
    // 테스트 환경에서는 세션 검증을 건너뛰고 항상 유효한 세션으로 간주
    next()
  } else {
    // 실제 환경에서는 세션 검증을 수행
    const isValid = await checkSession()
    if (isValid) {
      next() // 세션이 유효할 때만 라우팅 진행
    }
  }
})

// // 테스트할때 끄기
// import axios from 'axios'

// // 세션 검증 함수
// function checkSession() {
//   return axios
//     .get('https://mnow.kt.co.kr:8080/papi/guksa/check_session.php')
//     .then(response => {
//       if (!response.data.isValid) {
//         window.location.href = 'https://mnow.kt.co.kr:8080/guksa/member/login.html'
//         return false
//       }
//       return true
//     })
//     .catch(() => {
//       window.location.href = 'https://mnow.kt.co.kr:8080/guksa/member/login.html'
//       return false
//     })
// }

// // 라우터 가드 설정
// router.beforeEach(async (to, from, next) => {
//   const isValid = await checkSession()
//   if (isValid) {
//     next() // 세션이 유효할 때만 라우팅 진행
//   }
// })

/* eslint-disable import/order */
import '@/@iconify/icons-bundle'
import App from '@/App.vue'
import vuetify from '@/plugins/vuetify'
import { loadFonts } from '@/plugins/webfontloader'
import router from '@/router'
import { useAxiosStore } from '@/stores/axios'
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'
import '@styles/styles.scss'
import { createPinia } from 'pinia'
import { createApp } from 'vue'

loadFonts()

// Create vue app
const app = createApp(App)

// Use plugins
app.use(router)
app.use(vuetify)
app.use(createPinia())

//! 나중에 로그인 기능 있으면 옮겨야 함
const axiosStore = useAxiosStore()

axiosStore.initializeAxios()

// Mount vue app
app.mount('#app')
