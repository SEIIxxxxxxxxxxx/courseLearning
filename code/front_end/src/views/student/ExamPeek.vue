<!--
@name: ExamPeek.vue
@description: 获取测试
-->
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
        <v-card
          v-for="o in continueExamList"
          :key="o.id"
          :color="colorList[o.id % colorList.length]"
          class="ma-8 pa-4"
        >
          <v-card-title class="heading">
            课程ID: {{ o.courseId }}
          </v-card-title>

          <v-card-text class="mt-1">
            题目数量: {{ getQuestionNums(o.questionIdList) }}
          </v-card-text>
          <v-card-text class="mt-2">
            {{ o.startingTime }} - {{ o.endingTime }}
          </v-card-text>
          <v-card-actions class="pa-0 pl-2">
            <v-btn text @click="takeExam(o.id)">参加考试</v-btn>
          </v-card-actions>
        </v-card>
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
        <v-card
          v-for="o in overExamList"
          :key="o.id"
          :color="colorList[o.id % colorList.length]"
          class="ma-8 pa-4"
        >
          <v-card-title class="heading">
            课程ID: {{ o.courseId }}
          </v-card-title>

          <v-card-text class="mt-1">
            题目数量: {{ getQuestionNums(o.questionIdList) }}
          </v-card-text>
          <v-card-text class="mt-2">
            {{ o.startingTime }} - {{ o.endingTime }}
          </v-card-text>
          <v-card-actions class="pa-0 pl-2">
            <v-btn text @click="peekResult(o.id)">查看结果</v-btn>
          </v-card-actions>
        </v-card>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { getContinueExam, getOverExam } from "@/api/exam";

export default {
  inject: ["reload"],

  name: "ExamPeek",

  data() {
    return {
      continueExamList: [],
      overExamList: [],
      colorList: [
        "#FFAB91",
        "#26A69A",
        "#039BE5",
        "#546E7A",
        "#B39DDB",
        "#EF9A9A"
      ]
    };
  },

  methods: {
    fetchData() {
      this.handleContinueExams();
      this.handleOverExams();

      console.log(this.overExamList);
    },

    getQuestionNums(questionList) {
      const tmp_list = questionList.split("::");
      return tmp_list.length;
    },

    handleContinueExams() {
      const { courseId } = this.$route.params;
      getContinueExam(courseId).then(res => {
        this.continueExamList = res;
        console.log(this.continueExamList);
      });
    },

    handleOverExams() {
      const { courseId } = this.$route.params;
      getOverExam(courseId).then(res => {
        this.overExamList = res;
      });
    },
    /**
     *
     * @param examId
     * @description 进行中测试
     */
    takeExam(examId) {
      this.$router.push(`/student/takeExam/${examId}`);
    },
    /**
     *
     * @param examId
     * @description 已结束测试
     */
    peekResult(examId) {
      this.$router.push(`/student/PeekResult/${examId}`);
    }
  },

  mounted() {
    this.fetchData();
  }
};
</script>
