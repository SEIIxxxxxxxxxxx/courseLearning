<template>
  <div>
    <v-container class="pl-16 pr-16">
      <div v-show="examToDisplay !== ''">
        <v-alert icon="mdi-school" color="primary" elevation="3" top>
          您的得分为：
          <h1>{{ examToDisplay.score }}</h1>
          详细情况如下：
        </v-alert>

        <div v-for="(op, index) in questionInfoList" :key="op.id">
          <v-card class="mx-auto">
            <v-card-title>第{{ index + 1 }}题</v-card-title>

            <v-card-text>
              <p class="text-h5 text--primary">
                {{ op.stem }}
              </p>

              <div v-show="op.type === '单选题' || '多选题'">
                <div v-for="(oq, idd) in getOptions(op.option)" :key="oq">
                  <p class="text-sm-h6 text--primary">
                    {{ `${String.fromCharCode(idd + 65)}` }}： {{ `${oq}` }}
                  </p>
                </div>
              </div>

              <div v-show="trueOrFalseList[index] === '2'">
                <v-alert color="green" elevation="3" type="success">
                  正确答案为：
                  {{ `${trueAnswerList[index]}` }}, 你的答案为：
                  {{ `${userAnswerList[index]}` }}
                </v-alert>
              </div>

              <div v-show="trueOrFalseList[index] === '1'">
                <v-alert color="orange" elevation="3" type="warning">
                  正确答案为：
                  {{ `${trueAnswerList[index]}` }}, 你的答案为：
                  {{ `${userAnswerList[index]}` }}
                </v-alert>
              </div>

              <div v-show="trueOrFalseList[index] === '0'">
                <v-alert color="red" elevation="3" type="error">
                  正确答案为：
                  {{ `${trueAnswerList[index]}` }}, 你的答案为：
                  {{ `${userAnswerList[index]}` }}
                </v-alert>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </div>
      <div v-show="examToDisplay === ''">
        <v-alert icon="mdi-school" color="warning" elevation="3" top>
          您未参加该测试，测试答案如下：
        </v-alert>
        <div v-for="(op, index) in questionInfoList" :key="op.id">
          <v-card class="mx-auto">
            <v-card-title>第{{ index + 1 }}题</v-card-title>

            <v-card-text>
              <p class="text-h5 text--primary">
                {{ op.stem }}
              </p>

              <div v-show="op.type === '单选题' || '多选题'">
                <div v-for="(oq, idd) in getOptions(op.option)" :key="oq">
                  <p class="text-sm-h6 text--primary">
                    {{ `${String.fromCharCode(idd + 65)}` }}： {{ `${oq}` }}
                  </p>
                </div>
              </div>
              <v-alert color="blue" elevation="3" type="error">
                正确答案为：{{ `${questionInfoList[index].answer}` }}
              </v-alert>
            </v-card-text>
          </v-card>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import { getUserExam } from "@/api/userExam";
import { getExamById } from "@/api/exam";
import { getQuestionById } from "@/api/question";

export default {
  name: "PeekResult",
  inject: ["reload"],

  data() {
    return {
      examToDisplay: {
        userId: window.localStorage.getItem("userId"),
        examId: this.$route.params,
        score: 0,
        trueAnswer: "-1",
        userAnswer: "",
        trueOrFalse: "-1"
      },

      examTaken: {
        startTime: "",
        endTime: "",
        questionIdList: "",
        courseId: 0,
        teacherId: 0
      },

      questionList: [],
      questionInfoList: [],
      trueAnswerList: [],
      userAnswerList: [],
      trueOrFalseList: [],

      showSuccessDialog: false,
      showFailDialog: false,
      showCheckDialog: false,
      msg: ""
    };
  },

  methods: {
    refresh() {
      const { examId } = this.$route.params;
      const userId = window.localStorage.getItem("userId");
      getUserExam(userId, examId).then(res => {
        this.prepareExam(examId);
        this.examToDisplay = res;
        if (this.examToDisplay !== "") {
          this.prepareAnswer();
        }
      });
    },

    prepareExam(examId) {
      getExamById(examId).then(res => {
        this.examTaken = res;
        this.questionList = (this.examTaken.questionIdList || "").split("::");
        for (let i = 0; i < this.questionList.length; i++) {
          this.getQuestionInfo(this.questionList[i]);
        }
      });
    },

    getQuestionInfo(id) {
      getQuestionById(id).then(res => {
        this.questionInfoList.push(res.data);
      });
    },

    prepareAnswer() {
      this.trueAnswerList = this.examToDisplay.trueAnswer.split("::");
      this.userAnswerList = this.examToDisplay.userAnswer.split("::");
      this.trueOrFalseList = this.examToDisplay.trueOrFalse.split("::");
    },

    getOptions(str) {
      return (str || "").split("::");
    },

    getAnswer(str) {
      this.trueAnswerList.push(str);
    }
  },

  mounted() {
    this.refresh();
  }
};
</script>

<style scoped></style>
