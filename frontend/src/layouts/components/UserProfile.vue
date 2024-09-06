<template>
  <VBadge
    dot
    location="bottom right"
    offset-x="3"
    offset-y="3"
    color="success"
    bordered
  >
    <VAvatar
      class="cursor-pointer"
      color="primary"
      variant="tonal"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="2em"
        height="2em"
        viewBox="0 0 24 24"
      >
        <path
          fill="currentColor"
          d="M12 4a4 4 0 0 1 4 4a4 4 0 0 1-4 4a4 4 0 0 1-4-4a4 4 0 0 1 4-4m0 10c4.42 0 8 1.79 8 4v2H4v-2c0-2.21 3.58-4 8-4"
        />
      </svg>

      <!-- SECTION Menu -->
      <VMenu
        activator="parent"
        width="230"
        location="bottom end"
        offset="14px"
      >
        <VList>
          <!-- 👉 User Avatar & Name -->
          <VListItem>
            <template #prepend>
              <VListItemAction start>
                <VBadge
                  dot
                  location="bottom right"
                  offset-x="3"
                  offset-y="3"
                  color="success"
                >
                  <VAvatar
                    color="primary"
                    variant="tonal"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="2em"
                      height="2em"
                      viewBox="0 0 24 24"
                    >
                      <path
                        fill="currentColor"
                        d="M12 4a4 4 0 0 1 4 4a4 4 0 0 1-4 4a4 4 0 0 1-4-4a4 4 0 0 1 4-4m0 10c4.42 0 8 1.79 8 4v2H4v-2c0-2.21 3.58-4 8-4"
                      />
                    </svg>
                  </VAvatar>
                </VBadge>
              </VListItemAction>
            </template>

            <!-- <VListItemTitle class="font-weight-semibold"> {{ userName }}({{ userDept }}) </VListItemTitle> -->
            <VListItemSubtitle>사용자</VListItemSubtitle>
          </VListItem>

          <!-- Divider -->
          <VDivider class="my-2" />

          <!-- 👉 My Page -->
          <VListItem link>
            <template #prepend>
              <VIcon
                class="me-2"
                icon="bx-user"
                size="22"
              />
            </template>

            <VListItemTitle>마이페이지</VListItemTitle>
          </VListItem>

          <!-- Divider -->
          <VDivider class="my-1" />

          <!-- 👉 Logout -->
          <VListItem @click="logout">
            <template #prepend>
              <VIcon
                class="me-2"
                icon="bx-log-out"
                size="22"
              />
            </template>

            <VListItemTitle>로그아웃</VListItemTitle>
          </VListItem>
        </VList>
      </VMenu>
      <!-- !SECTION -->
    </VAvatar>
  </VBadge>
</template>

<script setup>
import axios from 'axios'

// const userName = ref(window.userData.name)
// const userDept = ref(window.userData.dept)

// 로그아웃 함수
function logout() {
  axios
    .post('https://mnow.kt.co.kr:8080/papi/guksa/logout.php')
    .then(() => {
      window.location.href = 'https://mnow.kt.co.kr:8080/guksa/member/login.html'
    })
    .catch(error => {
      console.error('Logout failed:', error)
    })
}
</script>
