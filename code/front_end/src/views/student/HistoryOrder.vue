<template>
  <div>
    <v-container class="ma-8 pa-4">
      <v-card
        v-for="o in orderList"
        :key="o.id"
        :color="colorList[o.id % colorList.length]"
        class="ma-8 pa-4"
      >
        <v-card-title class="headline">
          {{ o.courseName }}
        </v-card-title>

        <v-card-subtitle class="mt-1">
          购买时间: {{ o.createTime }}
        </v-card-subtitle>
        <v-card-actions class="pa-0 pl-2">
          <v-btn v-if="o.status === 1" text @click="handleStudy(o.courseId)"
            >学习课程</v-btn>
          <v-btn v-if="o.status !== 1" text>尚未支付</v-btn>
        </v-card-actions>
      </v-card>
      <v-card
        v-for="v in vipOrderList"
        :key="v.id"
        :color="colorList[v.id % colorList.length]"
        class="ma-8 pa-4"
      >
        <v-card-title class="headline">
          {{ items[v.type] }}
        </v-card-title>

        <v-card-subtitle class="mt-1">
          生效时间: {{ v.createTime }}
        </v-card-subtitle>
        <v-card-subtitle class="mt-1">
          失效时间: {{ v.endTime }}
        </v-card-subtitle>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import { getOrdersByUser } from "@/api/order";
import { getVipOrder } from "@/api/vip";

export default {
  name: "HistoryOrder",
  data() {
    return {
      colorList: [
        "#FFAB91",
        "#26A69A",
        "#039BE5",
        "#546E7A",
        "#B39DDB",
        "#EF9A9A"
      ],
      orderList: [],
      vipOrderList: [],
      items: ["", "贵族日卡", "尊贵月卡", "皇冠年卡"]
    };
  },

  methods: {
    handleStudy(courseId) {
      this.$router.push(`/student/course/${courseId}`);
    },
    getVipOrders(uid) {
      getVipOrder(uid).then(res => {
        console.log(res);
        this.vipOrderList = res || [];
      });
    }
  },

  mounted() {
    const uid = window.localStorage.getItem("userId");
    this.getVipOrders(uid);
    getOrdersByUser(uid).then(res => {
      console.log(res);
      this.orderList = res || [];
    });
  }
};
</script>
