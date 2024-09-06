module.exports = {
  env: {
    browser: true, //* 브라우저 환경을 대상으로 ESLint 규칙을 적용
    es2021: true, //* 최신 ECMAScript 기능을 사용할 수 있도록 설정
  },
  extends: [
    '.eslintrc-auto-import.json', //* 자동 임포트를 위한 설정 파일을 확장
    'plugin:vue/vue3-recommended', //* Vue.js 3 권장 설정을 적용
    'plugin:import/recommended', //* import 관련 권장 설정을 적용
    'plugin:promise/recommended', //* promise 관련 권장 설정을 적용
    'plugin:sonarjs/recommended', //* SonarJS 관련 권장 설정을 적용
    'plugin:prettier/recommended',

    // 'plugin:unicorn/recommended',
  ],
  parser: 'vue-eslint-parser', //* Vue 파일을 파싱하기 위해 vue-eslint-parser를 사용
  parserOptions: {
    ecmaVersion: 13, //* ECMAScript 13 버전의 문법을 사용
    sourceType: 'module', //* ES 모듈 시스템을 사용
  },
  plugins: [
    'vue', //* Vue.js 관련 규칙을 적용하기 위한 플러그인
    'regex', //* 정규 표현식 관련 규칙을 적용하기 위한 플러그인
    'prettier', // prettier 플러그인 추가
  ],
  ignorePatterns: ['src/@iconify/*.js', 'node_modules', 'dist'], //* ESLint가 무시할 파일 및 디렉토리
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off', //* 프로덕션 환경에서 console 사용 경고
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off', //* 프로덕션 환경에서 debugger 사용 경고

    'comma-spacing': ['error', { before: false, after: true }], //* 콤마 앞뒤의 간격 설정
    'key-spacing': ['error', { afterColon: true }], //* 키-값 쌍 사이의 간격 설정

    'vue/first-attribute-linebreak': [
      'error',
      {
        singleline: 'beside', //* 단일 줄의 경우 속성을 옆에 둠
        multiline: 'below', //* 다중 줄의 경우 속성을 아래에 둠
      },
    ],

    // 'vue/singleline-html-element-content-newline': 'off',

    // indentation (Already present in TypeScript)
    indent: ['error', 2], //* 들여쓰기 2칸 (TypeScript에 이미 존재)

    // Enforce trailing comma (Already present in TypeScript)
    'comma-dangle': ['error', 'always-multiline'], //* 항상 다중 줄에서 콤마를 사용 (TypeScript에 이미 존재)

    // Enforce consistent spacing inside braces of object (Already present in TypeScript)
    'object-curly-spacing': ['error', 'always'], //* 객체 중괄호 내에 공백을 항상 포함 (TypeScript에 이미 존재)

    // Disable max-len
    'max-len': 'off', //* 최대 줄 길이 규칙 비활성화

    // we don't want it
    semi: ['error', 'never'], //* 세미콜론 사용하지 않음

    // add parens ony when required in arrow function
    'arrow-parens': ['error', 'as-needed'], //* 필요한 경우에만 화살표 함수의 괄호 사용

    // add new line above comment
    'newline-before-return': 'error', //* return 문 앞에 새 줄 추가

    //* 주석 주변에 줄을 추가하는 규칙을 설정합니다.
    'lines-around-comment': [
      'error',
      {
        beforeBlockComment: true, //* 블록 주석 앞에 줄을 추가합니다.
        beforeLineComment: true, //* 라인 주석 앞에 줄을 추가합니다.
        allowBlockStart: true, //* 블록 시작 부분에서는 줄 추가를 허용합니다.
        allowClassStart: true, //* 클래스 시작 부분에서는 줄 추가를 허용합니다.
        allowObjectStart: true, //* 객체 시작 부분에서는 줄 추가를 허용합니다.
        allowArrayStart: true, //* 배열 시작 부분에서는 줄 추가를 허용합니다.
      },
    ],

    //* 배열 요소와 배열 괄호에 대해 일관된 줄바꿈을 적용합니다.
    'array-element-newline': ['error', 'consistent'], //* 배열 요소마다 일관된 줄바꿈을 적용합니다.
    'array-bracket-newline': ['error', 'consistent'], //* 배열 괄호 내에서 일관된 줄바꿈을 적용합니다.

    //* Vue 컴포넌트 이름에 대해 다중 단어 규칙을 비활성화합니다.
    'vue/multi-word-component-names': 'off',

    //* 특정 문장 유형 사이에 줄바꿈을 강제합니다.
    'padding-line-between-statements': [
      'error',
      { blankLine: 'always', prev: 'expression', next: 'const' }, //* 표현식 후 const 선언 전에 항상 줄바꿈을 추가합니다.
      { blankLine: 'always', prev: 'const', next: 'expression' }, //* const 선언 후 표현식 전에 항상 줄바꿈을 추가합니다.
      { blankLine: 'always', prev: 'multiline-const', next: '*' }, //* 다중 라인 const 선언 후 모든 유형 전에 항상 줄바꿈을 추가합니다.
      { blankLine: 'always', prev: '*', next: 'multiline-const' }, //* 모든 유형 후 다중 라인 const 선언 전에 항상 줄바꿈을 추가합니다.
    ],

    //* eslint-plugin-import 플러그인 관련 규칙을 설정합니다.
    'import/prefer-default-export': 'off', //* 기본 export 선호 규칙을 비활성화합니다.
    'import/newline-after-import': ['error', { count: 1 }], //* import 후에 새 줄을 하나 추가합니다.
    'no-restricted-imports': ['error', 'vuetify/components'], //* vuetify/components의 import를 제한합니다.

    //* TypeScript 파일의 확장자 생략을 허용합니다.
    'import/extensions': [
      'error',
      'ignorePackages',
      {
        js: 'never', //* .js 확장자는 생략합니다.
        jsx: 'never', //* .jsx 확장자는 생략합니다.
        ts: 'never', //* .ts 확장자는 생략합니다.
        tsx: 'never', //* .tsx 확장자는 생략합니다.
      },
    ],

    // 가상 파일을 무시합니다.
    'import/no-unresolved': [
      2,
      {
        ignore: [
          '~pages$', // ~pages 경로를 무시합니다.
          'virtual:generated-layouts', // virtual:generated-layouts 경로를 무시합니다.

          // Vite의 ?raw import를 무시합니다.
          '.*?raw',
        ],
      },
    ],

    // Thanks: https://stackoverflow.com/a/63961972/10796681
    'no-shadow': 'off',

    // Plugin: eslint-plugin-promise
    'promise/always-return': 'off',
    'promise/catch-or-return': 'off',

    // ESLint plugin vue
    'vue/block-tag-newline': 'error',
    'vue/component-api-style': 'error',
    'vue/component-name-in-template-casing': ['error', 'PascalCase', { registeredComponentsOnly: false }],
    'vue/custom-event-name-casing': [
      'error',
      'camelCase',
      {
        ignores: ['/^(click):[a-z]+((d)|([A-Z0-9][a-z0-9]+))*([A-Z])?/'],
      },
    ],
    'vue/define-macros-order': 'error',
    'vue/html-comment-content-newline': 'error',
    'vue/html-comment-content-spacing': 'error',
    'vue/html-comment-indent': 'error',
    'vue/match-component-file-name': 'error',
    'vue/no-child-content': 'error',
    'vue/require-default-prop': 'off',

    // NOTE this rule only supported in SFC,  Users of the unplugin-vue-define-options should disable that rule: https://github.com/vuejs/eslint-plugin-vue/issues/1886
    // 'vue/no-duplicate-attr-inheritance': 'error',
    'vue/no-empty-component-block': 'error',
    'vue/no-multiple-objects-in-class': 'error',
    'vue/no-reserved-component-names': 'error',
    'vue/no-template-target-blank': 'error',
    'vue/no-useless-mustaches': 'error',
    'vue/no-useless-v-bind': 'error',
    'vue/padding-line-between-blocks': 'error',
    'vue/prefer-separate-static-class': 'error',
    'vue/prefer-true-attribute-shorthand': 'error',
    'vue/v-on-function-call': 'error',
    'vue/no-restricted-class': ['error', '/^(p|m)(l|r)-/'],
    'vue/valid-v-slot': [
      'error',
      {
        allowModifiers: true,
      },
    ],

    // -- Extension Rules
    'vue/no-irregular-whitespace': 'error',
    'vue/template-curly-spacing': 'error',

    // -- Sonarlint
    'sonarjs/no-duplicate-string': 'off',
    'sonarjs/no-nested-template-literals': 'off',

    // -- Unicorn
    // 'unicorn/filename-case': 'off',
    // 'unicorn/prevent-abbreviations': ['error', {
    //   replacements: {
    //     props: false,
    //   },
    // }],

    // https://github.com/gmullerb/eslint-plugin-regex
    'regex/invalid': [
      'error',
      [
        {
          regex: '@/assets/images',
          replacement: '@images',
          message: "Use '@images' path alias for image imports",
        },
        {
          regex: '@/styles',
          replacement: '@styles',
          message: "Use '@styles' path alias for importing styles from 'src/styles'",
        },

        // {
        //   id: 'Disallow icon of icon library',
        //   regex: 'tabler-\\w',
        //   message: 'Only \'mdi\' icons are allowed',
        // },

        {
          regex: '@core/\\w',
          message: "You can't use @core when you are in @layouts module",
          files: {
            inspect: '@layouts/.*',
          },
        },
        {
          regex: 'useLayouts\\(',
          message:
            '`useLayouts` composable is only allowed in @layouts & @core directory. Please use `useThemeConfig` composable instead.',
          files: {
            inspect: '^(?!.*(@core|@layouts)).*',
          },
        },

        //? axios는 pinia로 사용중
        // {
        //   regex: 'import axios from \'axios\'',
        //   replacement: 'import axios from \'@axios\'',
        //   message: 'Use axios instances created in \'src/plugin/axios.js\' instead of unconfigured axios',
        //   files: {
        //     ignore: '^.*plugins/axios.js.*',
        //   },
        // },
      ],

      // Ignore files
      '.eslintrc.js',
    ],
  },
  settings: {
    'import/resolver': {
      node: {
        extensions: ['.js', '.js', '.jsx', '.jsx', '.mjs', '.png', '.jpg'],
      },
      alias: {
        extensions: ['.ts', '.js', '.tsx', '.jsx', '.mjs'],
        map: [
          ['@', './src'],
          ['@core', './src/@core'],
          ['@layouts', './src/@layouts'],
          ['@images', './src/assets/images/'],
          ['@styles', './src/styles/'],
          ['@configured-variables', './src/styles/variables/_template.scss'],
          ['@axios', './src/plugins/axios'],
          ['apexcharts', 'node_modules/apexcharts-clevision'],
        ],
      },
    },
  },
}
