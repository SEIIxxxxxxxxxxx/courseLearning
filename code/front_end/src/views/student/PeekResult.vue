<template>
  <div>
    // TODO 展示内容

  </div>
</template>

<script>
import { getUserExamById } from "@/api/exam";

export default {
  name: "PeekResult",

  data(){
    return{
      userExamInfo:{
        userId: window.localStorage.getItem("userId"),
        examId: this.$route.params,
        score: 0,
        trueAnswer: "",
        userAnswer: "",
        trueOrFalse : ""
      },

      examToCheck: {
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
      const examId_  = this.$route.params;
      const userId_ = window.localStorage.getItem("userId");
      getUserExamById(examId_,userId_).then(res => {
        this.examToCheck = res;
      });
    },
  }
};
</script>

<style scoped>

</style>
