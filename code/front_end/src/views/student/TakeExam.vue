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
    <v-container class="pl-16 pr-16">
      <div v-for="(q, index) in questionInfoList" :key="q.id">
        <v-text-field
          v-model="q.stem"
          readonly
          :label="`第${index + 1}题`"
        ></v-text-field>
        <v-textarea
          v-show="q.type === '问答题'"
          label="答案"
          v-model="userAnswerList[index]"
          clearable
          :counter="200"
          :rules="rules.AreaRules"
        ></v-textarea>
        <div v-show="q.type === '单选题'">
          {{ "请选择唯一正确的答案" }}
          <v-radio-group v-model="userAnswerList[index]">
            <v-radio
              v-for="(n, idd) in getOptions(q.option)"
              :key="idd"
              :value="String.fromCharCode(65 + idd)"
              :label="`${String.fromCharCode(idd + 65)} ： ${n}`"
            ></v-radio>
          </v-radio-group>
        </div>
        <div v-if="q.type === '多选题'">
          {{ "请选择正确的全部答案" }}
          <dl>
            <dt>
              <v-checkbox
                v-for="(op, idx) in getOptions(q.option)"
                :key="idx"
                :label="`${String.fromCharCode(idx + 65)} ： ${op}`"
                :value="String.fromCharCode(idx + 65)"
                v-model="multiSelected[index]"
                color="success"
              ></v-checkbox>
            </dt>
          </dl>
        </div>
      </div>
      <v-btn class="ml-0 mt-8 info" @click="submit">
        提交
      </v-btn>
      <!--      <v-btn class="ml-0 mt-8 info" @click="test">-->
      <!--        测试-->
      <!--      </v-btn>-->
    </v-container>
  </div>
</template>

<script>
import { createUserExam, getExamById } from "@/api/exam";
import { getQuestionById } from "@/api/question";

export default {
  name: "TakeExam",
  inject: ["reload"],
  data() {
    return {
      userExamInfo: {
        userId: window.localStorage.getItem("userId"),
        examId: this.$route.params,
        score: 0,
        trueAnswer: "-1",
        userAnswer: "",
        trueOrFalse: "-1"
      },

      examToTake: {
        startTime: "",
        endTime: "",
        questionIdList: "",
        courseId: 0,
        teacherId: 0
      },
      questionList: [],
      questionInfoList: [],
      userAnswerList: [],
      multiChoicesNum: [],
      multiSelected: [],
      showSuccessDialog: false,
      showFailDialog: false,
      showCheckDialog: false,
      rules: {
        AreaRules: [
          v => !!v || "提示: 不能为空",
          v => v.length <= 200 || "提示: 输入超过字数限制"
        ],
        OptionRules: [
          v => !!v || "提示: 不能为空",
          v => v.length <= 50 || "提示: 输入超过字数限制"
        ]
      },
      msg: ""
    };
  },
  methods: {
    // test() {
    //   console.log(this.multiSelected);
    //   console.log(this.multiChoicesNum);
    //   this.formatUserAnswerList();
    //   console.log(this.userExamInfo.userAnswer);
    // },
    refresh() {
      const { examId } = this.$route.params;
      getExamById(examId).then(res => {
        this.examToTake = res;
        this.questionList = (this.examToTake.questionIdList || "").split("::");
        for (let i = 0; i < this.questionList.length; i++) {
          this.getAQuestion(this.questionList[i]);
          this.userAnswerList.push("");
        }
      });
    },

    initMultiOptions(idx, flag) {
      if (flag) {
        this.multiChoicesNum.push(idx);
      }
      this.multiSelected.push([]);
    },

    getAQuestion(id) {
      getQuestionById(id).then(res => {
        let i = this.questionInfoList.push(res.data);
        if (res.data.type === "多选题") {
          this.initMultiOptions(i - 1, true);
        } else {
          this.initMultiOptions(i - 1, false);
        }
      });
    },

    getOptions(str) {
      return (str || "").split("::");
    },

    formatTrueAnswer() {
      let tmp = "";
      for (let i = 0; i < this.questionInfoList.length; i++) {
        if (i !== 0) {
          tmp += "::";
        }
        tmp += this.questionInfoList[i].answer;
      }
      this.userExamInfo.trueAnswer = tmp;
    },

    formatMultiAnswer() {
      for (let i = 0; i < this.multiChoicesNum.length; i++) {
        let idx = this.multiChoicesNum[i];
        let tmp = "";
        let ans = this.multiSelected[idx].sort();
        for (let j = 0; j < ans.length; j++) {
          tmp += ans[j];
        }
        this.userAnswerList[idx] = tmp;
      }
    },

    formatUserAnswerList() {
      this.formatMultiAnswer();
      for (let i = 0; i < this.userAnswerList.length; i++) {
        if (i !== 0) {
          this.userExamInfo.userAnswer += "::";
        }
        this.userExamInfo.userAnswer += this.userAnswerList[i];
      }
      console.log(this.userExamInfo.userAnswer);
    },

    submit() {
      this.formatUserAnswerList();
      this.formatTrueAnswer();
      const e = {
        userId: this.userExamInfo.userId,
        examId: this.userExamInfo.examId.examId,
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

<style scoped></style>
