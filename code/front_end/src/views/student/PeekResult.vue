<template>
  <div>
    <v-container class="pl-16 pr-16">
      您的得分为：{{examToDisplay.score}}
      作答情况如下：

      <li v-for="(op, index) in questionList" :key="op.id">
        <form class="pa-12 grey lighten-5 mt-8">
          第{{ (index+1) }}题：
          {{op.stem}}

          <div v-show="op.type === '单选题'">
            <ul>
              <li v-for="(op, index) in allOptionsForEachQuestion[index]" :key="op.id">
                <!-- TODO:显示题干（只显示不可选）-->
                "`${n.id} ： ${n.option}`"
              </li>

              <!-- TODO:v-alert -->
              <div v-show="trueOrFalseList[index] === '1'">
                <v-alert
                  color="green"
                  elevation="3"
                  outlined
                  text
                  type="success"
                  正确答案为：{{trueAnswerList[index]}}
                  你的答案为：{{userAnswerList[index]}}
                ></v-alert>
              </div>
              <div v-show="trueOrFalseList[index] === '0'">
                <v-alert
                  color="red"
                  elevation="3"
                  text
                  type="warning"
                  正确答案为：{{trueAnswerList[index]}}
                  你的答案为：{{userAnswerList[index]}}
                ></v-alert>
              </div>

            </ul>
          </div>

          <div v-show="op.type === '多选题'">
            <ul>
              <li v-for="(op, index) in allOptionsForEachQuestion[index]" :key="op.id">
                <!-- TODO:显示题干（只显示不可选）-->
                "`${n.id} ： ${n.option}`"
              </li>
              <!-- TODO:v-alert -->
              <div v-show="trueOrFalseList[index] === '1'">
                <v-alert
                  color="green"
                  elevation="3"
                  outlined
                  text
                  type="success"
                  正确答案为：{{trueAnswerList[index]}}
                  你的答案为：{{userAnswerList[index]}}
                ></v-alert>
              </div>
              <div v-show="trueOrFalseList[index] === '0'">
                <v-alert
                  color="red"
                  elevation="3"
                  text
                  type="warning"
                  正确答案为：{{trueAnswerList[index]}}
                  你的答案为：{{userAnswerList[index]}}
                ></v-alert>
              </div>
            </ul>
          </div>

          <!-- 目前未考虑问答题-->

        </form>
      </li>
    </v-container>
  </div>
</template>

<script>
import { getUserExam } from "@/api/exam";
import { getExamById } from "../../api/exam";
import { getQuestionById } from "../../api/question";

export default {
  name: "PeekResult",

  data() {
    return {
      examToDisplay: {
        userId: window.localStorage.getItem("userId"),
        examId: this.$route.params,
        score: 0,
        trueAnswer: "",
        userAnswer: "",
        trueOrFalse: ""
      },

      questionIdList: [],
      questionList: [],
      allOptionsForEachQuestion: [],

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
      const examId = this.$route.params;
      const userId = window.localStorage.getItem("userId");
      getUserExam(examId, userId).then(res => {
        this.examToDisplay = res;
      });

      getExamById( examId ).then(res => {
        this.examToTake = res;
        let questionNoList = [];
        questionNoList = this.examToTake.questionIdList.split("::")
        for (let i = 0; i < questionNoList.length; i++) {
          <!--TODO:bug to solve: 同TakeExam -->
          this.questionList.push(getQuestionById(questionNoList[i]));
        }
      });
    }
  },

  prepareQuestionOptions() {
    let ret = [];
    for(let i=0;i<this.questionList.length;i++){
      let temp = this.questionList[i].str_option.split("::");
      for(let j=0;j<temp.length;j++){
        ret.push({id:String.fromCharCode(j + 65), option:temp[j]});
      }
    }
    this.allOptionsForEachQuestion = ret;
  },

  prepareAnswerLists(){
    this.trueAnswerList = this.examToDisplay.trueAnswer.split("::");
    this.userAnswerList = this.examToDisplay.userAnswer.split("::");
    this.trueOrFalseList = this.examToDisplay.trueOrFalse.split("::");
  },

  mounted() {
    this.refresh();
    this.prepareQuestionOptions();
    this.prepareAnswerLists();
  }
};
</script>

<style scoped></style>
