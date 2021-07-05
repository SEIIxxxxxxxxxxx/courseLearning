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
      <li v-for="(op, index) in questionList" :key="op.id">
       <form class="pa-12 grey lighten-5 mt-8">
         第{{ (index+1) }}题：
         {{op.stem}}

         <div v-show="op.type === '单选题'">
           <v-radio-group v-model="userAnswerList[index]">
             <v-radio
               v-for="(n,index) in allOptionsForEachQuestion[index]"
               :key="n.id"
               :label="`${n.id} ： ${n.option}`"
             ></v-radio>
           </v-radio-group>
         </div>

         <div v-show="op.type === '多选题'">
           <ul>
             <li v-for="(op, index) in allOptionsForEachQuestion[index]" :key="op.id">
               <v-checkbox
                 :label="`${op.id} ： ${op.option}`"
                 v-model="userAnswerList[index].option"
                 color="success"
               ></v-checkbox>
             </li>
           </ul>
         </div>

         <v-textarea
           v-show="op.type === '问答题'"
           label="答题区"
           v-model="userAnswerList[index].option"
           clearable
           :counter="200"
           :rules="areaRules"
         ></v-textarea>

       </form>
      </li>
      <v-btn class="ml-0 mt-8 info" @click="submit">
        提交测试
      </v-btn>
    </v-container>
  </div>
</template>

<script>
import { getExamById } from "../../api/exam";
import { createUserExam } from "../../api/exam";
import { getQuestionById } from "../../api/question";

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
        startingTime: "",
        endingTime: "",
        questionIdList: "",
        courseId: 0,
        teacherId: 0
      },

      questionToAnswer:{
        id: 0,
        stem: "",
        type: "",
        str_option: "",
        answer: "",
        analysis: "",
        courseID: 0
      },

      questionList: [],
      userAnswerList: [],
      allOptionsForEachQuestion: [],

      showSuccessDialog: false,
      showFailDialog: false,
      showCheckDialog: false,


      areaRules: [
        v => !!v || "提示: 不能为空",
        v => v.length <= 200 || "提示: 输入超过字数限制"
      ],

      msg: "",
    }

  },
  methods: {
    refresh() {
      const { examId } = this.$route.params;
      getExamById( examId ).then(res => {
        this.examToTake = res;
        let questionNoList = [];
        questionNoList = this.examToTake.questionIdList.split("::")
        for (let i = 0; i < questionNoList.length; i++) {
          <!--TODO:bug to solve: get不到数据，是前端的问题,后端测试过应该没问题 -->
          this.questionList.push(getQuestionById(questionNoList[i]));
        }
      });
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


    formatUserAnswerList(){
      for(let i=0;i<this.userAnswerList.length;i++){
        this.userExamInfo.userAnswer += this.userAnswerList[i];
        this.userExamInfo.userAnswer += "::";
      }
      this.userExamInfo.userAnswer = this.userExamInfo.userAnswer.substr(
        0,
        this.userExamInfo.userAnswer - 2
      );
    },

    setTrueAnswer(){
      for(let i=0;i<this.questionList.length;i++){
        this.userExamInfo.trueAnswer += this.questionList[i].answer;
        this.userExamInfo.trueAnswer += "::";
      }
      this.userExamInfo.trueAnswer = this.userExamInfo.trueAnswer.substr(
        0,
        this.userExamInfo.trueAnswer - 2
      );
    },

    submit() {
      this.formatUserAnswerList();
      this.setTrueAnswer();

      const e = {
        ...this.userExamInfo,
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
    this.prepareQuestionOptions();
  }

};
</script>


<style scoped>

</style>
