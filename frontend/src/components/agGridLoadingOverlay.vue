<template>
  <VRow
    justify="center"
    class="h-100"
  >
    <VCol cols="12">
      <VSkeletonLoader type="table" />
    </VCol>
    <VSheet
      position="absolute"
      style="top: 30%; left: 50%"
      elevation="20"
      class="loader d-flex justify-center align-center text-h6 py-4 px-10"
      rounded
    >
      로딩중
    </VSheet>
  </VRow>
</template>
