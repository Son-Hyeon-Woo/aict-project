import vue from '@vitejs/plugin-vue' //* Vue 플러그인 불러오기
import vueJsx from '@vitejs/plugin-vue-jsx' //* Vue JSX 플러그인 불러오기
import { fileURLToPath } from 'node:url' //* URL을 파일 경로로 변환하기 위한 유틸리티 함수 불러오기
import AutoImport from 'unplugin-auto-import/vite' //* 자동으로 import를 관리해주는 플러그인 불러오기
import Components from 'unplugin-vue-components/vite' //* Vue 컴포넌트를 자동으로 import해주는 플러그인 불러오기
import { defineConfig } from 'vite' //* Vite 설정을 정의하기 위한 함수 불러오기
import vuetify from 'vite-plugin-vuetify' //* Vuetify 플러그인 불러오기

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),

    // Docs: https://github.com/vuetifyjs/vuetify-loader/tree/master/packages/vite-plugin
    vuetify({
      styles: {
        configFile: 'src/styles/variables/_vuetify.scss', //* Vuetify 스타일 설정 파일 경로
      },
    }),
    Components({
      dirs: ['src/@core/components'], //* 자동으로 import할 컴포넌트들이 있는 디렉토리
      dts: true, //! TypeScript용 Declaration 파일 생성, 없어도 상관없음 (타입스크립트 사용하는거 아니라면)
    }),

    // Docs: https://github.com/antfu/unplugin-auto-import#unplugin-auto-import
    AutoImport({
      eslintrc: {
        enabled: true, //* ESLint 설정 파일 생성 여부
        filepath: './.eslintrc-auto-import.json', //* 생성될 ESLint 설정 파일 경로
      },
      imports: ['vue', 'vue-router', '@vueuse/core', '@vueuse/math', 'pinia'], //* 자동으로 import할 라이브러리 목록
      vueTemplate: true, //* Vue 템플릿에서도 자동 import 적용
    }),
  ],
  define: { 'process.env': {} }, //* 환경 변수를 설정 (여기서는 빈 객체로 설정)
  resolve: {
    alias: {
      //* 각 문자열을 디렉토리 주소로 매핑시키는 코드
      //? '@': fileURLToPath(new URL('./src', import.meta.url)), '@'를 'src' 디렉토리로 매핑
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@core': fileURLToPath(new URL('./src/@core', import.meta.url)),
      '@layouts': fileURLToPath(new URL('./src/@layouts', import.meta.url)),
      '@images': fileURLToPath(new URL('./src/assets/images/', import.meta.url)),
      '@styles': fileURLToPath(new URL('./src/styles/', import.meta.url)),
      '@configured-variables': fileURLToPath(new URL('./src/styles/variables/_template.scss', import.meta.url)),
      apexcharts: fileURLToPath(new URL('node_modules/apexcharts-clevision', import.meta.url)),
      primevue: fileURLToPath(new URL('node_modules/primevue', import.meta.url)),
    },
  },
  build: {
    chunkSizeWarningLimit: 5000, //* 청크 파일 크기 경고 한계 설정 (기본값: 500KB)
    //? 큰 JS 를 작은 JS 여러개로 분리
    //? 로딩시간 단축 및 NW 트래픽 줄어드는 효과 (필요한 JS를 그때 로딩해서 사용함)

    //? .js 파일 만들때 해쉬 재생성
    rollupOptions: {
      output: {
        // 파일 이름 패턴 설정
        chunkFileNames: 'assets/[name]-[hash].js',
        entryFileNames: 'assets/[name]-[hash].js',
        assetFileNames: 'assets/[name]-[hash][extname]',
        manualChunks(id) {
          // LayoutWithVerticalNav.vue 파일을 별도의 청크로 분리
          if (id.includes('default.vue')) {
            return 'default_v240827'
          }
          if (id.includes('LayoutWithVerticalNav.vue')) {
            return 'layout-with-vertical-nav_v240827'
          }

          // node_modules 디렉토리에 있는 라이브러리들도 별도의 청크로 분리
          if (id.includes('node_modules')) {
            return id.toString().split('node_modules/')[1].split('/')[0].toString()
          }
        },
      },
    },
  },
  optimizeDeps: {
    exclude: ['vuetify'], //? vuetify 최적화는 아직  vite에서 완전히 호환되지 않는다고 한다 at 240528 by sonhw
    entries: ['./src/**/*.vue'],
  },
})
