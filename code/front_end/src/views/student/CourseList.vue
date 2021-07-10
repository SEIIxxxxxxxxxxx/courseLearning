<template>
  <div>
    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="success"
      text
      v-show="showSuccessDialog"
      transition="scroll-y-transition"
    >
      成功！
    </v-alert>
    <v-container class="ma-8 pa-4">
      <!-- 热门课程 -->
      <v-row class="mt-8 mb-2">
        <v-chip
          class="ma-2"
          color="deep-purple lighten-3"
          label
          text-color="white"
        >
          <v-icon left>
            mdi-label
          </v-icon>
          热门课程
        </v-chip>
      </v-row>
      <v-row>
        <course-item
          cols="12"
          md="4"
          v-for="course in hotCourseList"
          :key="course.id"
          :courseName="course.name"
          :courseId="course.id"
          :description="course.intro"
          :type="course.type"
          :cost="course.cost"
          :bought="course.bought"
          :manageable="course.manageable"
          :course-color="colorList[course.id % colorList.length]"
          :course-likes="course.likes"
          :liked="course.liked"
          @buy-course="showDialog"
          @rent-course="showDialog"
          @set-like="setLikeOrDislike"
        >
        </course-item>
      </v-row>

      <template>
        <!-- 分类课程 -->
        <v-row class="mt-2 mb-8">
          <v-chip
            class="ma-2"
            color="deep-purple lighten-3"
            label
            text-color="white"
          >
            <v-icon left>
              mdi-label
            </v-icon>
            分类课程
          </v-chip>
        </v-row>
        <v-card>
          <v-tabs color="deep-purple accent-4" right v-model="currentTab">
            <v-tab>初级课程</v-tab>
            <v-tab>中级课程</v-tab>
            <v-tab>高级课程</v-tab>

            <v-tab-item v-for="c in [primary, medium, advanced]" :key="c.name">
              <v-container fluid>
                <v-row class="ma-4">
                  <course-item
                    cols="12"
                    md="4"
                    v-for="course in c.list"
                    :key="course.id"
                    :courseName="course.name"
                    :courseId="course.id"
                    :description="course.intro"
                    :type="course.type"
                    :cost="course.cost"
                    :bought="course.bought"
                    :rent="course.rent"
                    :manageable="course.manageable"
                    :course-color="colorList[course.id % colorList.length]"
                    :course-likes="course.likes"
                    :liked="course.liked"
                    @buy-course="showDialog"
                    @rent-course="showDialog"
                    @set-like="setLikeOrDislike"
                  >
                  </course-item>
                </v-row>
                <v-pagination
                  v-model="c.currentPage"
                  :length="c.totalPage"
                  circle
                ></v-pagination>
              </v-container>
            </v-tab-item>
          </v-tabs>
        </v-card>
      </template>

      <v-row class="ma-12">
        <v-divider></v-divider>
      </v-row>

      <!-- 搜索课程 -->
      <v-row class="mt-10">
        <v-col>
          <v-chip color="deep-purple lighten-3" label text-color="white">
            <v-icon left>
              mdi-label
            </v-icon>
            搜索课程
          </v-chip>
        </v-col>
        <v-col>
          <v-text-field
            v-model="searchText"
            outlined
            label="搜索课程"
            append-icon="mdi-magnify"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <course-item
          cols="12"
          md="4"
          v-for="course in searchCourse"
          :key="course.id"
          :courseName="course.name"
          :courseId="course.id"
          :description="course.intro"
          :type="course.type"
          :cost="course.cost"
          :bought="course.bought"
          :rent="course.rent"
          :manageable="course.manageable"
          :course-color="colorList[course.id % colorList.length]"
          :course-likes="course.likes"
          :liked="course.liked"
          @buy-course="showDialog"
          @rent-course="showDialog"
          @set-like="setLikeOrDislike"
        >
        </course-item>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-pagination
          class="mt-4"
          v-model="searchCurrentPage"
          :length="searchTotalPage"
          circle
        ></v-pagination>
      </v-row>

      <v-row class="ma-12">
        <v-divider></v-divider>
      </v-row>

      <!-- 已购课程 -->
      <v-row class="mt-8 mb-2">
        <v-chip
          class="ma-2"
          color="deep-purple lighten-3"
          label
          text-color="white"
        >
          <v-icon left>
            mdi-label
          </v-icon>
          我的已购课程
        </v-chip>
      </v-row>
      <v-row>
        <course-item
          cols="12"
          md="4"
          v-for="course in boughtCoursesList"
          :key="course.id"
          :courseName="course.name"
          :courseId="course.id"
          :description="course.intro"
          :type="course.type"
          :cost="course.cost"
          :bought="course.bought"
          :rent="course.rent"
          :manageable="course.manageable"
          :course-color="colorList[course.id % colorList.length]"
          :course-likes="course.likes"
          :liked="course.liked"
          @buy-course="showDialog"
          @rent-course="showDialog"
          @set-like="setLikeOrDislike"
        >
        </course-item>
      </v-row>
    </v-container>

    <!-- 租用提示对话框 -->
    <v-dialog v-model="dialog3" width="1000">
      <v-card>
        <v-card-title>租用信息确认</v-card-title>
        <v-card-text> 是否租用课程 「{{ currentCourseName }}」？ </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="
              settleDialog3 = true;
              orderType = 2;
            "
          >
            选择租用方案
          </v-btn>
          <v-btn color="primary" text @click="dialog3 = false">
            取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="settleDialog3" width="1000">
      <v-card>
        <v-card-title>租用方案</v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-select
                  :items="rentTimeList"
                  label="选择租用时长（天）"
                  v-model="rentTime"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  label="租用费用（元）"
                  v-show="rentTime === 7"
                  v-model="rentMapper[rentTime]"
                  readonly
                ></v-text-field>
                <v-text-field
                  label="租用费用（元）"
                  v-show="rentTime === 15"
                  v-model="rentMapper[rentTime]"
                  readonly
                ></v-text-field>
                <v-text-field
                  label="租用费用（元）"
                  v-show="rentTime === 30"
                  v-model="rentMapper[rentTime]"
                  readonly
                ></v-text-field>
              </v-col>
            </v-row>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="primary"
                v-show="rentTime !== 0"
                text
                @click="openSettleDialog3"
              >
                确认租用方案
              </v-btn>
              <v-btn color="primary" text @click="settleDialog3 = false">
                取消
              </v-btn>
            </v-card-actions>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- 租用支付 -->
    <v-dialog v-model="settleDialog4" width="1000">
      <v-card>
        <v-card-title>租用支付确认</v-card-title>
        <v-card-text
          >是否支付{{ rentCost }}元租用课程「{{ currentCourseName }}」{{
            rentTime
          }}天？</v-card-text
        >
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="confirmPayment">
            确认支付
          </v-btn>
          <v-btn color="primary" text @click="leaveSettlement">
            取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 购买提示对话框 -->
    <v-dialog v-model="dialog" width="1000">
      <v-card>
        <v-card-title>购买信息确认</v-card-title>
        <v-card-text> 是否购买课程 「{{ currentCourseName }}」？ </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="
              openSettleDialog();
              orderType = 1;
            "
          >
            选择结算方案
          </v-btn>
          <v-btn color="primary" text @click="dialog = false">
            取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="settleDialog" width="1000">
      <v-card>
        <v-card-title v-if="msg">{{ msg }}</v-card-title>
        <div v-else>
          <v-card-title>优惠券选择</v-card-title>
          <v-data-table
            v-model="selectedCoupons"
            :headers="couponHeaders"
            :items="coupons"
            class="elevation-1"
            show-select
            @item-selected="onSelectCoupon"
          >
            <template #header.data-table-select></template>
          </v-data-table>
          <v-row justify="center" class="mt-5"> </v-row>
          <v-card-actions class="justify-space-around d-flex">
            <v-alert type="info">课程原价： {{ currentOrder.origin }}</v-alert>
            <v-alert type="success">实际付款： {{ currentOrder.cost }}</v-alert>
            <v-btn color="primary" text @click="confirmPayment">
              确认支付</v-btn
            >
            <v-btn color="secondary" text @click="leaveSettlement"> 离开</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>

    <!-- 购买结果 -->
    <v-dialog v-model="dialog2" width="500">
      <v-card>
        <v-card-title>购买课程</v-card-title>

        <v-card-text>
          {{ msg }}
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialog2 = false">
            确认
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="showSnackBar" :color="snackBarColor" top>
      {{ snackBarMsg }}
    </v-snackbar>
  </div>
</template>

<script>
import CourseItem from "@/components/CourseItem.vue";
import {
  getCoursesByType,
  getCoursesByKey,
  getBoughtCourses,
  getHotCourses,
  setCourseLike,
  getCourseById,
  setCourseDislike
} from "@/api/course";
import {
  cancelCoupon,
  createOrder,
  getOrderById,
  payOrder,
  useCoupon
} from "@/api/order";
import { getAvailableCouponsForOrder } from "@/api/coupon";

export default {
  //刷新
  inject: ["reload"],

  name: "StudentCourseList",

  components: {
    CourseItem
  },

  data() {
    return {
      dialog: false,
      dialog2: false,
      dialog3: false,
      msg: "",
      currentCourseId: 0,
      currentCourseName: "",
      currentCoursePrice: 0,
      orderType: 0,
      colorList: ["#26A69A", "#00B0FF", "#5C6BC0", "#FFB300", "#E57373"],
      currentTab: 0, // 0 1 2
      primary: {
        name: "初级",
        totalPage: 1,
        currentPage: 1,
        list: []
      },
      medium: {
        name: "中级",
        totalPage: 1,
        currentPage: 1,
        list: []
      },
      advanced: {
        name: "高级",
        totalPage: 1,
        currentPage: 1,
        list: []
      },
      searchText: "",
      searchCourse: [],
      searchTotalPage: 1,
      searchCurrentPage: 1,
      boughtCoursesList: [],
      hotCourseList: [],
      showSnackBar: false,
      snackBarMsg: "",
      snackBarColor: "success",
      couponHeaders: [
        {
          text: "优惠券名称",
          value: "name"
        },
        {
          text: "优惠券类型",
          value: "type"
        },
        {
          text: "优惠券描述",
          value: "description"
        },
        {
          text: "元数据",
          value: "metadata"
        },
        {
          text: "与其他优惠券同时使用",
          value: "sharable"
        }
      ],
      coupons: [],
      selectedCoupons: [],
      settleDialog: false,
      settleDialog3: false,
      settleDialog4: false,
      selectRentFlag: true,
      showSuccessDialog: false,
      currentOrder: {},
      rentTimeList: [7, 15, 30],
      rentTime: 0,
      rentCost: 0,
      rentMapper: {
        7: 1,
        15: 2,
        30: 3
      }
    };
  },

  watch: {
    "primary.currentPage": {
      handler: function(val) {
        this.handleGetCoursesByType(val, "primary");
      }
    },

    "medium.currentPage": {
      handler: function(val) {
        this.handleGetCoursesByType(val, "medium");
      }
    },

    "advance.currentPage": {
      handler: function(val) {
        this.handleGetCoursesByType(val, "advanced");
      }
    },

    searchCurrentPage: function() {
      this.handleSearchCourse();
    },

    searchText: function() {
      this.handleSearchCourse();
    }
  },

  methods: {
    showDialog(courseId, courseName, coursePrice, ordType) {
      this.currentCourseId = courseId;
      this.currentCourseName = courseName;
      this.currentCoursePrice = coursePrice;
      this.orderType = ordType;
      if (ordType === 1) {
        this.dialog = true;
      } else {
        this.dialog3 = true;
      }
    },

    openSettleDialog() {
      const uid = window.localStorage.getItem("userId");
      createOrder(uid, this.currentCourseId, this.orderType).then(res => {
        if (res.code === 0) {
          this.snackBarColor = "error";
          this.snackBarMsg = res.msg;
          this.showSnackBar = true;
          this.dialog = false;
          return;
        }

        this.dialog = false;
        this.settleDialog = true;
        this.currentOrder = res.data;

        getAvailableCouponsForOrder(this.currentOrder.id).then(res => {
          this.selectedCoupons = this.currentOrder.usedCoupons
            ? this.currentOrder.usedCoupons
            : [];

          this.coupons = [...res.data, ...this.selectedCoupons]; // 将当前可用的 和 已经用的 合并起来展示，若之前创建过订单没有付款，再次进入时需要这样
        });
      });
    },

    openSettleDialog3() {
      const uid = window.localStorage.getItem("userId");
      createOrder(uid, this.currentCourseId, this.orderType).then(res => {
        if (res.code === 0) {
          this.snackBarColor = "error";
          this.snackBarMsg = res.msg;
          this.showSnackBar = true;
          this.dialog3 = false;
          return;
        }
        this.rentCost = this.rentMapper[this.rentTime];
        this.dialog3 = false;
        this.settleDialog4 = true;
        this.currentOrder = res.data;
      });
    },

    handleGetCoursesByType(page, type) {
      const uid = window.localStorage.getItem("userId");
      const typeMap = { primary: "初级", medium: "中级", advanced: "高级" };
      getCoursesByType({ uid, type: typeMap[type], page }).then(res => {
        this[type].totalPage = res.pages;
        this[type].list = res.list;
      });
    },

    handleSearchCourse() {
      const uid = window.localStorage.getItem("userId");
      getCoursesByKey({
        uid,
        key: this.searchText,
        page: this.searchCurrentPage
      }).then(res => {
        this.searchCourse = res.list;
        this.searchTotalPage = res.pages;
      });
    },

    handleHotCourse() {
      const uid = window.localStorage.getItem("userId");
      getHotCourses(uid).then(res => {
        this.hotCourseList = res.list;
      });
    },

    getUserBoughtCourses() {
      const uid = window.localStorage.getItem("userId");
      getBoughtCourses(uid).then(res => {
        this.boughtCoursesList = res || [];
      });
    },

    fetchData() {
      ["primary", "medium", "advanced"].forEach(t => {
        this.handleGetCoursesByType(1, t);
      });
      this.handleSearchCourse();
      this.getUserBoughtCourses();
      this.handleHotCourse();
    },

    onSelectCoupon({ item, value }) {
      if (value) {
        useCoupon(this.currentOrder, item.id).then(res => {
          this.snackBarColor = "success";
          this.snackBarMsg = res.msg;
          this.showSnackBar = true;
          getOrderById(this.currentOrder.id).then(res => {
            this.currentOrder = res.data;
          });
        });
      } else {
        cancelCoupon(this.currentOrder, item.id).then(res => {
          this.snackBarColor = "success";
          this.snackBarMsg = res.msg;
          this.showSnackBar = true;
          getOrderById(this.currentOrder.id).then(res => {
            this.currentOrder = res.data;
          });
        });
      }
    },
    // 该方法绑定到了课程卡牌的心形按钮上
    setLikeOrDislike(courseId) {
      const uid = window.localStorage.getItem("userId");
      // 通过一个接口来获取该用户对当前课程的点赞情况，根据结果进入不同的分支

      getCourseById({ courseId, uid }).then(res => {
        if (res.liked) {
          // 分支2：若当前用户已经为该课程点过赞了，则调用取消点赞接口完成相关操作，可以仿照分支1的示例进行

          setCourseDislike(uid, courseId).then(res => {
            if (res.code === 1) {
              this.snackBarMsg = res.msg;
              this.snackBarColor = "success";
              this.showSnackBar = true;
              this.fetchData();
            }
          });
        } else {
          // 分支1：若当前用户没有对该课程点赞则调用点赞接口
          setCourseLike(uid, courseId).then(res => {
            if (res.code === 1) {
              //点赞成功，设置提示条的内容，颜色，并显示
              this.snackBarMsg = res.msg;
              this.snackBarColor = "success";
              this.showSnackBar = true;
              // 点赞成功后，课程的点赞数会变化，也会影响热门课程的排序，因此调用以下方法更新页面中的数据
              this.fetchData();
            } else {
              // 点赞失败，设置提示条的内容、颜色，并显示。目前这里显示的内容是： 点赞失败、请勿重复点赞。
              // 如果你的代码实现正确（以及后端代码） 应当不会出现这种情况。
              // 原因是在重复点赞前，你应当在这里已经判断出点过赞，因此应当跳转到分支2去执行取消点赞的逻辑
              this.snackBarMsg = res.msg;
              this.snackBarColor = "warning";
              this.showSnackBar = true;
            }
          });
        }
      });
    },
    /**
     * @description 跳转至历史订单界面
     */
    goToOrderList() {
      this.$router.push("/student/history");
    },
    /**
     * @description 确认支付订单
     */
    confirmPayment() {
      this.settleDialog = false;
      this.settleDialog4 = false;
      this.settleDialog3 = false;
      payOrder(this.currentOrder.id, this.rentTime, this.rentCost).then(res => {
        if (res && res.code === 1) {
          this.snackBarColor = "success";
          this.snackBarMsg = res.msg;
          this.showSnackBar = true;
          this.reload();
        } else {
          this.goToOrderList();
        }
      });
    },
    leaveSettlement() {
      this.currentOrder = {};
      this.currentCourseId = 0;
      this.currentCoursePrice = 0;
      this.currentCourseName = "";
      this.coupons = [];
      this.selectedCoupons = [];
      this.settleDialog = false;
      this.settleDialog4 = false;
      this.settleDialog3 = false;
    }
  },

  mounted() {
    this.fetchData();
  }
};
</script>
