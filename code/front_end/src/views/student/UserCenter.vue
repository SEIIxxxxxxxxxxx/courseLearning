<template>
  <div>
    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="success"
      text
      v-show="showAlert"
      transition="scroll-y-transition"
    >
      {{ msg }}
    </v-alert>
    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="warning"
      text
      v-show="showFailDialog"
      transition="scroll-y-transition"
    >
      {{ msg }}
    </v-alert>
    <template>
      <v-dialog v-model="dialog" max-width="400">
        <v-card>
          <v-card-title class="headline">
            账户充值
          </v-card-title>
          <v-text-field
            v-model="value"
            label="充值金额"
            class="pa-6"
          ></v-text-field>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="green darken-1" text @click="handleRecharge">
              充值
            </v-btn>

            <v-btn color="green darken-1" text @click="dialog = false">
              取消
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>

    <template>
      <v-dialog v-model="dialog2" max-width="400">
        <v-card>
          <v-card-title class="headline">
            购买会员
          </v-card-title>
          <v-col class="d-flex" cols="12" sm="6">
            <v-select
              :items="items"
              label="选择类型"
              dense
              solo
              v-model="type"
            ></v-select>
          </v-col>
          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="green darken-1" text @click="handleVip">
              确认购买
            </v-btn>

            <v-btn color="green darken-1" text @click="dialog2 = false">
              取消购买
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>

    <v-container class="pl-16 pr-16">
      <form class="pa-12 grey lighten-5 mt-8">
        <v-text-field v-model="userInfo.id" label="ID" readonly></v-text-field>
        <v-text-field
          v-model="userInfo.uname"
          label="用户名"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="userInfo.phone"
          label="手机号"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="userInfo.userRole"
          label="用户角色"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="userInfo.createTime"
          label="注册时间"
          readonly
        ></v-text-field>
        <v-text-field
          v-model="userInfo.balance"
          label="账户余额"
          readonly
        ></v-text-field>
        <div>
          <v-btn color="primary" dark @click.stop="dialog = true">
            充值
          </v-btn>
        </div>
        <v-text-field
          v-if="endTime === ''"
          label="用户级别"
          v-model="normal"
          readonly
        ></v-text-field>
        <v-text-field
          v-if="endTime !== ''"
          label="用户级别"
          v-model="vip"
          readonly
        ></v-text-field>
        <div>
          <v-btn color="primary" dark @click.stop="dialog2 = true">
            购买会员
          </v-btn>
        </div>
        <v-text-field
          v-if="endTime !== ''"
          v-model="endTime"
          label="会员到期时间"
          readonly
        ></v-text-field>
      </form>
    </v-container>
  </div>
</template>

<script>
import { getUser } from "@/api/user";
import { recharge } from "@/api/recharge";
import { createVipOrder, getVipEndTime } from "@/api/vip";

export default {
  name: "UserCenter",

  data() {
    return {
      userInfo: {
        id: 0,
        uname: "",
        phone: "",
        password: null,
        picture: null,
        balance: 0,
        userRole: "",
        createTime: ""
      },
      dialog: false,
      dialog2: false,
      showAlert: false,
      showFailDialog: false,
      normal: "普通用户",
      vip: "尊贵会员",
      value: 0,
      type: null,
      items: ["贵族日卡：￥5", "尊贵月卡：￥20", "皇冠年卡：￥180"],
      endTime: "",
      msg: ""
    };
  },

  methods: {
    handleRecharge() {
      recharge({ userId: this.userInfo.id, value: this.value }).then(res => {
        console.log(res);
        if (res && res.code === 1) {
          this.msg = res.msg;
          this.dialog = false;
          this.showAlert = true;
          this.refreshUserInfo();
          setTimeout(() => {
            this.showAlert = false;
          }, 1000);
        }
      });
    },

    getVipEndTime() {
      const userId = window.localStorage.getItem("userId");
      getVipEndTime(userId).then(res => {
        this.endTime = res || "";
      });
    },

    convertType() {
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i] === this.type) {
          this.type = i + 1;
        }
      }
    },
    /**
     * @description 处理VIP购买请求
     */
    handleVip() {
      this.convertType();
      console.log(this.type);
      createVipOrder(this.userInfo.id, this.type).then(res => {
        console.log(res);
        if (res && res.code === 1) {
          this.msg = res.msg;
          this.dialog2 = false;
          this.showAlert = true;
          this.refreshUserInfo();
          setTimeout(() => {
            this.showAlert = false;
          }, 1000);
        } else {
          this.msg = res.msg;
          this.dialog2 = false;
          this.showFailDialog = true;
          setTimeout(() => {
            this.showFailDialog = false;
          }, 1000);
        }
      });
    },

    refreshUserInfo() {
      const userId = window.localStorage.getItem("userId");
      getUser(userId).then(res => {
        this.userInfo = res || {};
        this.getVipEndTime();
      });
    }
  },

  mounted() {
    this.refreshUserInfo();
  }
};
</script>

<style scoped>
.alert {
  position: fixed;
  left: 50%;
  top: 100px;
  z-index: 999;
}
</style>
