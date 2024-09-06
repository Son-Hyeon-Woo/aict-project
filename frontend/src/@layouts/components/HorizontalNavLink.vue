<script setup>
const props = defineProps({
  item: {
    type: null,
    required: true,
  },
})

const route = useRoute()

const nowRouter = computed(() => {
  const pathSegments = route.path.split('/')

  return '/' + pathSegments[pathSegments.length - 1]
})

const isActive = computed(() => props.item.to === nowRouter.value)
</script>

<template>
  <RouterLink :to="item.to">
    <VBtn
      :variant="isActive ? 'tonal' : 'text'"
      size="x-large"
      class="nav-link mx-2 px-3 d-none d-lg-block"
      :class="{ disabled: item.disable, 'd-none': item.dNone, active: isActive }"
    >
      {{ item.title }}
    </VBtn>
  </RouterLink>
</template>

<style lang="scss" scoped>
button {
  color: #5e5e5e !important;
  font-weight: 600;

  &.active {
    color: rgb(var(--v-theme-primary)) !important;
    font-weight: 800;
  }
}
</style>
