<template>
  <div>
    <v-container class="ma-8 pa-4">
      <!-- 进行中测试 -->
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
          进行中测试
        </v-chip>
      </v-row>
      <v-row>
        //TODO 显示进行中测试信息，循环中嵌套下面的按钮
        <v-btn class="ml-0 mt-8 info" @click="takeExam(item.id)">
          参加测试
        </v-btn>

      </v-row>
      <!-- 已结束测试 -->
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
          已结束测试
        </v-chip>
      </v-row>
      <v-row>
        // TODO 显示已结束测试信息，循环中嵌套下面的按钮
        <v-btn class="ml-0 mt-8 info" @click="peekResult(item.id)">
          查看结果
        </v-btn>

      </v-row>
    </v-container>
  </div>
</template>

<script>

import {
  getContinueExam,
  getOverExam
} from "@/api/exam";

export default {
  inject: ["reload"],

  name: "ExamPeek",

  data(){
    return {
      continueExamList: [],
      overExamList:[]
    }
  },

  methods:{
    fetchData() {
      this.handleContinueExams();
      this.handleOverExams();
    },

    handleContinueExams(){
      const cid = window.localStorage.getItem("courseId");
      getContinueExam(cid).then(res => {
          this.continueExamList = res.list;
        });
    },

    handleOverExams(){
      const cid = window.localStorage.getItem("courseId");
      getOverExam(cid).then(res => {
        this.overExamList = res.list;
      });
    },

    takeExam(examId){
      this.$router.push(`/student/takeExam/${examId}`);
    },

    peekResult(examId){
      this.$router.push(`/student/PeekResult/${examId}`);
    }
  },

  mounted() {
    this.fetchData();
  }
};
</script>
