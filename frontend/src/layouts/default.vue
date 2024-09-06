<script setup>
import LayoutWithVerticalNav from './components/LayoutWithVerticalNav.vue'
</script>

<template>
  <LayoutWithVerticalNav>
    <RouterView />
  </LayoutWithVerticalNav>
</template>

<style lang="scss">
// As we are using `layouts` plugin we need its styles to be imported
@use '@layouts/styles/default-layout';
</style>
