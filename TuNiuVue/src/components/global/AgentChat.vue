<template>
  <div class="agent-fab">
    <!-- 悬浮按钮 -->
    <div class="fab-btn" @click="open = !open" :title="open ? '收起智能助手' : '途牛智能助手'">
      <el-icon v-if="!open"><ChatDotRound/></el-icon>
      <el-icon v-else><Close/></el-icon>
    </div>

    <!-- 聊天面板 -->
    <transition name="agent-slide">
      <div v-if="open" class="agent-panel">
        <div class="agent-header">
          <div class="agent-title">
            <el-icon><Service/></el-icon>
            <span>途牛智能助手</span>
          </div>
          <span class="agent-status">在线</span>
        </div>

        <div class="agent-body" ref="bodyRef">
          <div v-for="(msg, idx) in messages" :key="idx" :class="['msg', msg.role]">
            <div class="avatar">
              <el-icon v-if="msg.role === 'user'"><User/></el-icon>
              <el-icon v-else><Service/></el-icon>
            </div>
            <div class="bubble">{{ msg.content }}</div>
          </div>
          <div v-if="loading" class="msg assistant">
            <div class="avatar"><el-icon><Service/></el-icon></div>
            <div class="bubble typing">正在思考中<span class="dot">.</span><span class="dot">.</span><span class="dot">.</span></div>
          </div>
        </div>

        <div class="agent-footer">
          <el-input
              v-model="input"
              type="textarea"
              :rows="2"
              resize="none"
              placeholder="问我车次、城市、订单…"
              @keydown.enter.exact.prevent="send"
          />
          <el-button type="primary" :loading="loading" @click="send" class="send-btn">
            <el-icon><Promotion/></el-icon>发送
          </el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import {ref} from 'vue'
// 模块级共享状态：供 Navi 菜单等外部组件触发打开助手面板
export const agentOpen = ref(false)
</script>

<script setup>
import {nextTick, ref} from 'vue'
import axios from 'axios'
import {ElMessage} from 'element-plus'

// 复用模块级共享状态，菜单与浮窗按钮均可控制开关
const open = agentOpen
const input = ref('')
const loading = ref(false)
const bodyRef = ref(null)
const messages = ref([
  {
    role: 'assistant',
    content: '您好，我是途牛智能助手，可以帮您查询城市、车票和订单信息，请问有什么可以帮您？'
  }
])

const scrollToBottom = async () => {
  await nextTick()
  if (bodyRef.value) {
    bodyRef.value.scrollTop = bodyRef.value.scrollHeight
  }
}

const send = async () => {
  const text = input.value.trim()
  if (!text || loading.value) return
  messages.value.push({role: 'user', content: text})
  input.value = ''
  loading.value = true
  await scrollToBottom()

  try {
    const userId = sessionStorage.getItem('userId')
    const res = await axios.post('/agent/chat', {
      message: text,
      userId: userId ? Number(userId) : null
    })
    const data = res.data
    if (data && data.code === 200 && data.data) {
      messages.value.push({role: 'assistant', content: data.data.reply || '(空回复)'})
    } else {
      messages.value.push({role: 'assistant', content: data?.msg || '服务异常，请稍后重试'})
    }
  } catch (e) {
    const msg = e.response?.data?.msg || e.message || '网络错误'
    messages.value.push({role: 'assistant', content: '抱歉，服务暂时不可用：' + msg})
    ElMessage.warning('智能助手请求失败')
  } finally {
    loading.value = false
    await scrollToBottom()
  }
}
</script>

<style scoped lang="scss">
.agent-fab {
  position: fixed;
  right: 28px;
  bottom: 28px;
  z-index: 9999;
}

/* 悬浮按钮 */
.fab-btn {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 26px;
  cursor: pointer;
  box-shadow: 0 4px 16px rgba(64, 158, 255, 0.4);
  transition: transform 0.2s, box-shadow 0.2s;
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(64, 158, 255, 0.55);
  }
}

/* 聊天面板 */
.agent-panel {
  position: absolute;
  right: 0;
  bottom: 72px;
  width: 360px;
  height: 520px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.agent-header {
  height: 52px;
  background: #409eff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  .agent-title {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 15px;
    font-weight: 600;
  }
  .agent-status {
    font-size: 12px;
    background: rgba(255, 255, 255, 0.25);
    padding: 2px 8px;
    border-radius: 10px;
  }
}

.agent-body {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background: #f5f7fa;
  .msg {
    display: flex;
    margin-bottom: 14px;
    align-items: flex-start;
    gap: 8px;
    .avatar {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      background: #409eff;
      color: #fff;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 16px;
      flex-shrink: 0;
    }
    .bubble {
      max-width: 230px;
      padding: 8px 12px;
      border-radius: 8px;
      font-size: 13px;
      line-height: 1.5;
      word-break: break-word;
      white-space: pre-wrap;
    }
    &.user {
      flex-direction: row-reverse;
      .avatar {
        background: #67c23a;
      }
      .bubble {
        background: #409eff;
        color: #fff;
      }
    }
    &.assistant {
      .bubble {
        background: #fff;
        color: #303133;
        border: 1px solid #e4e7ed;
      }
    }
    .typing .dot {
      animation: blink 1.4s infinite both;
      &:nth-child(2) { animation-delay: 0.2s; }
      &:nth-child(3) { animation-delay: 0.4s; }
    }
  }
}

.agent-footer {
  padding: 10px 12px;
  border-top: 1px solid #ebeef5;
  background: #fff;
  .send-btn {
    margin-top: 8px;
    width: 100%;
  }
}

@keyframes blink {
  0%, 80%, 100% { opacity: 0; }
  40% { opacity: 1; }
}

/* 展开动画 */
.agent-slide-enter-active,
.agent-slide-leave-active {
  transition: transform 0.25s ease, opacity 0.25s ease;
}
.agent-slide-enter-from,
.agent-slide-leave-to {
  transform: translateY(20px) scale(0.95);
  opacity: 0;
}
</style>
