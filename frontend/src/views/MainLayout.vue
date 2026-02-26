<script setup lang="ts">
import { ref, computed } from 'vue'
import UserManagement from '../views/UserManagement.vue'

const activeTab = ref('users')

const tabs = computed(() => [
  {
    key: 'users',
    title: '用户管理',
    component: UserManagement
  }
])

const handleTabChange = (key: string) => {
  activeTab.value = key
}
</script>

<template>
  <div class="main-container">
    <div class="sidebar">
      <div class="logo">
        <h2>系统管理</h2>
      </div>
      <div class="menu">
        <div
          v-for="tab in tabs"
          :key="tab.key"
          class="menu-item"
          :class="{ active: activeTab === tab.key }"
          @click="handleTabChange(tab.key)"
        >
          <span>{{ tab.title }}</span>
        </div>
      </div>
    </div>
    <div class="content">
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane
          v-for="tab in tabs"
          :key="tab.key"
          :label="tab.title"
          :name="tab.key"
        >
          <component :is="tab.component" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped>
.main-container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 240px;
  background: #001529;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #001529;
}

.logo h2 {
  color: #fff;
  font-size: 20px;
  margin: 0;
}

.menu {
  flex: 1;
  padding-top: 16px;
}

.menu-item {
  height: 48px;
  display: flex;
  align-items: center;
  padding-left: 24px;
  cursor: pointer;
  transition: all 0.3s;
}

.menu-item:hover {
  background: #1890ff;
}

.menu-item.active {
  background: #1890ff;
}

.content {
  flex: 1;
  padding: 20px;
  overflow: auto;
  background: #f0f2f5;
}

.content :deep(.el-tabs) {
  height: calc(100vh - 40px);
}

.content :deep(.el-tabs__content) {
  height: calc(100vh - 100px);
  overflow: auto;
}
</style>
