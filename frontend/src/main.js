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
