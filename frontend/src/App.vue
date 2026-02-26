<script setup lang="ts">
import MainLayout from './views/MainLayout.vue'
</script>

<template>
  <div id="app">
    <MainLayout />
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  width: 100%;
  height: 100vh;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial,
    'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol',
    'Noto Color Emoji';
}

html,
body {
  width: 100%;
  height: 100%;
}
</style>
