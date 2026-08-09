<script setup>
import AgentChat from '@/components/global/AgentChat.vue'
</script>

<template>
  <router-view></router-view>
  <!-- Agent 智能助手浮窗（全局可用，不影响原有主题） -->
  <AgentChat/>
</template>

<style scoped>

</style>
