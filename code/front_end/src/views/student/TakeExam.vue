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
      提交测试成功！
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
    <!-- alert -->
    <v-alert
      class="alert"
      outlined
      type="warning"
      text
      v-show="showCheckDialog"
      transition="scroll-y-transition"
    >
      请检查信息是否完整填写且格式正确！
    </v-alert>

  </div>
</template>

<script>
import { getExamById } from "@api/exam";
import { createUserExam } from "@/api/exam";

export default {
  name: "TakeExam",
  data(){
    return {
      userExamInfo: {
        userId: window.localStorage.getItem("userId"),
        examId: this.$route.params,
        score: 0,
        trueAnswer: "",
        userAnswer: "",
        trueOrFalse: ""
      },

      examToTake: {
        startTime: "",
        endTime: "",
        questionIdList: "",
        courseId: 0,
        teacherId: 0
      },

      showSuccessDialog: false,
      showFailDialog: false,
      showCheckDialog: false,
      msg: "",
    }

  },
  methods: {
    refresh() {
      const { examId } = this.$route.params;
      getExamById(examId).then(res => {
        this.examToTake = res;
      });
    },

    //TODO 转换
    formatUserAnswerList(){},

    submit() {
      this.formatUserAnswerList();

      const e = {
        userId: this.userExamInfo.userId,
        examId: this.userExamInfo.examId,
        score: this.userExamInfo.score,
        trueAnswer: this.userExamInfo.trueAnswer,
        userAnswer: this.userExamInfo.userAnswer,
        trueOrFalse: this.userExamInfo.trueOrFalse
      };
      console.log(e);


      createUserExam(e).then(res => {
        console.log(res);
        if (res.code === 1) {
          this.showSuccessDialog = true;
          setTimeout(() => {
            this.reload();
          }, 1000);
        } else {
          this.showFailDialog = true;
          this.msg = res.msg;
          setTimeout(() => {
            this.showFailDialog = false;
          }, 1000);
        }
      });
    }
  },

  mounted() {
    this.refresh();
  }

};
</script>


<style scoped>

</style>
