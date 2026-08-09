<template>
  <div class="orderItem" @click="toPay">
      <div class="up">
        <el-checkbox v-model="only" size="small" />
        <span>订单号：<span class="number">{{is.id}}</span></span>
        <span>预定日期：{{formattedDate(is.time)}}</span>
          <span class="button" :style="{opacity: deleting ? 0.6 : 1, pointerEvents: deleting ? 'none' : 'auto'}" @click.stop.prevent="del">删除订单</span>
      </div>
      <div class="down">
        <div class="left">
          <h1 v-if="is.trainTickets">{{is.trainTickets.startStation}}——{{is.trainTickets.endStation}}</h1>
          <h1 v-else>—</h1>
          <h2 v-if="is.trainTickets">出发日期：{{NoSecond(is.trainTickets.startTime)}} 至 {{hourAndMinute(is.trainTickets.endTime)}} {{is.trainTickets.trainNumber}}</h2>
          <h2 v-else>出发日期：—</h2>
          <h2>出行人：{{passenger}}</h2>
        </div>
        <div class="right">
          <h2>{{is.status}}</h2>
          <h1>￥{{is.totalPrice}}</h1>
        </div>
      </div>
  </div>
</template>

<script setup>
import {formattedDate,NoSecond,hourAndMinute} from '@/hook/timeFormat.js'
import {computed, ref} from "vue";
import axios from "axios";
import router from "@/router/index.js";
const haha = defineProps(['is'])
const passenger = computed(()=>{
  const arr = haha.is.passenger || haha.is.passengers || []
  return arr.map(item=>item.name).join('、')
})

const deleting = ref(false)

const del = async () => {
  if (deleting.value) return
  if (!confirm('你确认删除吗?')) return
  deleting.value = true
  try {
    const resp = await axios.post('orders/del?id=' + haha.is.id)
    // 后端现在返回 Result：{code,msg,data}
    const ok = resp?.data?.code === 200
    alert(ok ? (resp?.data?.msg || '删除成功') : (resp?.data?.msg || '删除失败'))
    router.go(0)
  } catch (e) {
    alert('删除失败：网络或服务器错误')
  } finally {
    deleting.value = false
  }
}

const toPay = (e) => {
  // 删除过程中不允许跳转支付
  if (deleting.value) {
    if (e?.preventDefault) e.preventDefault()
    if (e?.stopPropagation) e.stopPropagation()
    return
  }
  // 通过 axios.defaults.baseURL 统一网关地址，避免硬编码
  window.open(axios.defaults.baseURL + '/alipay/pay?orderId=' + haha.is.id + "&totalPrice=" + haha.is.totalPrice,"_self")
}
</script>

<style scoped lang="scss">
.orderItem{
  //overflow-y: auto;
  width: 100%;
  height: 112px;
  //background-color: pink;
  margin-top: 20px;
  border: rgb(217,217,217) 1px solid;
  cursor: pointer;
  &:hover{
    .down{
      background-color: rgb(247,251,255);
    }
  }
  .up{
    height: 30px;
    line-height: 30px;
    font-size: 13px;
    background-color: rgb(246,246,246);
    span{
      margin-left: 15px;
    }
    .number{
      margin-left: -5px;
      cursor: pointer;
      color: #409eff;
      &:hover{
        text-decoration: underline;
      }
    }
    .button{
      cursor: pointer;
      color: #409eff;
      &:hover{
        text-decoration: underline;
      }
    }
  }
  .down{
    width: 100%;
    height: 80px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .left{
      padding-left: 30px;
      h1{
        font-size: 16px;
        font-weight: 700;
        margin-top: 7px;
      }
      h2{
        font-size: 11px;
        font-weight: 500;
        margin-top: 5px;
      }
    }
    .right{
      padding-right: 30px;
      text-align: center;
      h1{
        font-size: 16px;
        font-weight: 700;
      }
      h2{
        font-size: 13px;
        font-weight: 500;
      }
    }
  }
}
</style>