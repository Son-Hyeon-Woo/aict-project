<script setup>
import VerticalNavLink from '@/@layouts/components/VerticalNavLink.vue';
import TransitionExpand from './TransitionExpand.vue';

const props = defineProps({
  item: {
    type: Object,
    required: true,
  },
  isOpened: {
    type: Boolean,
    default: true,
  },
  isNoDisplay: {
    type: Boolean,
    default: false,
  },
})

const route = useRoute()
const categoryName = inject('categoryName')
const toValues = props.item.items.map(item => item.to)

// isActive는 현재 라우트가 자식 링크 중 하나와 일치하는지 확인합니다.
const isActive = computed(() => {  
  return props.item.items.some(subItem => {
    const path1 = route.path.split('/')[1]
    const path2 = subItem.to.split('/')[1]
    if (toValues.includes(route.path)) {
      categoryName.value = props.item.title
    }
    
    return path1 === path2
  })
})

const isOpen = ref(props.isOpened)
const is_no_display = ref(props.isNoDisplay)

const toggleOpen = () => {
  isOpen.value = !isOpen.value
}
</script>

<template>
  <li
    class="nav-group"
    :class="{ open: isOpen, active: isActive, disabled: item.disabled, 'd-none': is_no_display }"
  >
    <div
      class="nav-group-label"
      @click="toggleOpen"
    >
      <VIcon
        :icon="item.icon"
        class="nav-item-icon"
      />
      <span class="nav-item-title">{{ item.title }}</span>
      <VIcon
        :icon="isOpen ? 'mdi-chevron-up' : 'mdi-chevron-down'"
        class="nav-group-arrow"
      />
    </div>
    <TransitionExpand>
      <ul
        v-if="isOpen"
        class="nav-group-children"
      >
        <VerticalNavLink
          v-for="subItem in item.items"
          :key="subItem.title"
          :item="subItem"
          icon="material-symbols:radio-button-unchecked"
        />
      </ul>
    </TransitionExpand>
  </li>
</template>

<style lang="scss" scoped>
.nav-group {
  .nav-group-label {
    display: flex;
    align-items: center;
    cursor: pointer;
  }

  &.active {
    .nav-group-label {
      color: var(--v-primary);
      font-weight: bold;
    }
  }
}
</style>
