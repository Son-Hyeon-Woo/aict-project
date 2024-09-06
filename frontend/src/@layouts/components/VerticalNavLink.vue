<script setup>
const props = defineProps({
  item: {
    type: null,
    required: true,
  },
  icon: {
    type: String,
    required: true,
  },
})

const route = useRoute()

// console.log(route.path.split('/')[1])
// console.log(props.item)

// isActive는 현재 라우트가 자식 링크 중 하나와 일치하는지 확인합니다.
const isActive = computed(() => {  
  
  const path1 = route.path.split('/')[1]
  const path2 = props.item.to.split('/')[1]
    
  return path1 === path2
})
</script>

<template>
  <li
    class="nav-link"
    :class="{ disabled: item.disable, 'd-none': item.dNone }"
  >
    <Component
      :is="item.to ? 'RouterLink' : 'a'"
      :to="item.to"
      :href="item.href"
      :class="{ 'router-link-exact-active': isActive }"
    >
      <VIcon
        :icon="icon"
        class="nav-item-icon"
      />
      <!-- 👉 Title -->
      <span class="nav-item-title">
        {{ item.title }}
      </span>
    </Component>
  </li>
</template>

<style lang="scss">
.layout-vertical-nav {
  .nav-link a {
    display: flex;
    align-items: center;
    cursor: pointer;
  }
}
</style>
