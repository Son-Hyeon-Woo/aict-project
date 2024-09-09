<template>
  <div class="d-flex align-center">
    <VIcon
      :icon="props.icon"
      class="me-2"
    />
    <span
      :style="{ fontSize: props.fontSize, fontWeight: props.fontWeight }"
      style="line-height: 0"
    >
      {{ props.title }}
    </span>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true,
    default: () => '',
  },
  fontSize: {
    type: String,
    default: () => '1rem',
  },
  fontWeight: {
    type: String,
    default: () => '500',
  },
  icon: {
    type: String,
    default: () => 'hugeicons:menu-square',
  },
})
</script>
