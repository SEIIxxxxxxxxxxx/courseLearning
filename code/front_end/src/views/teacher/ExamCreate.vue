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
      测试创建成功！
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
      v-show="showReDialog"
      transition="scroll-y-transition"
    >
      该课程下没有相应题目！
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
      <form class="pa-12 grey lighten-5 mt-8">
        <v-text-field
          v-model="examInfo.courseId"
          label="课程编号"
        ></v-text-field>

        <v-btn class="ml-0 mt-8 info" @click="searchQuestions">
          查询可选题目
        </v-btn>

        <div v-if="questionToBeChosen.length !== 0">
          {{ "可选题目：" }}
          <ul>
            <li v-for="(c, index) in questionToBeChosen" :key="c.id">
              <v-checkbox
                :label="`${c.id} ： ${c.stem}`"
                v-model="questionIdMap[index].isSelected"
                color="success"
              ></v-checkbox>
            </li>
          </ul>
        </div>

        <v-text-field
          v-model="examInfo.startTime"
          label="开始时间"
          messages="YYYY-MM-DD HH:MM:SS"
        ></v-text-field>
        <v-text-field
          v-model="examInfo.endTime"
          label="结束时间"
          messages="YYYY-MM-DD HH:MM:SS"
        ></v-text-field>

        <v-btn class="ml-0 mt-8 info" @click="submit">
          确认
        </v-btn>
      </form>
    </v-container>
  </div>
</template>

<script>
import { createExam } from "@/api/exam";
import { getAvailableQuestionsForCourseId } from "@/api/exam";

export default {
  name: "ExamCreate",

  data() {
    return {
      examInfo: {
        startTime: "",
        endTime: "",
        questionIdList: "",
        courseId: 0,
        teacherId: window.localStorage.getItem("userId")
      },

      questionToBeChosen: [],
      questionIdMap: [],

      showSuccessDialog: false,
      showFailDialog: false,
      showCheckDialog: false,
      showReDialog: false,
      msg: ""
    };
  },

  methods: {
    formatQuestionList() {
      for (let i = 0; i < this.questionIdMap.length; i++) {
        if (this.questionIdMap[i].isSelected) {
          this.examInfo.questionIdList += this.questionIdMap[i].qid;
          this.examInfo.questionIdList += "::";
        }
      }
      this.examInfo.questionIdList = this.examInfo.questionIdList.substr(
        0,
        this.examInfo.questionIdList.length - 2
      );
    },

    prepareQuestionList() {
      console.log(this.questionToBeChosen);
      if (this.questionToBeChosen.length === 0) {
        this.showReDialog = true;
        setTimeout(() => {
          this.showReDialog = false;
        }, 1000);
      } else {
        console.log(this.questionIdMap);
        for (let i = 0; i < this.questionToBeChosen.length; i++) {
          this.questionIdMap.push({ qid: i + 1, isSelected: false });
        }
      }
    },

    searchQuestions() {
      let id = this.examInfo.courseId;
      getAvailableQuestionsForCourseId(id).then(res => {
        this.questionToBeChosen = res || [];
        this.prepareQuestionList();
      });
    },

    submit() {
      this.formatQuestionList();

      const e = {
        startTime: this.examInfo.startTime,
        endTime: this.examInfo.endTime,
        questionIdList: this.examInfo.questionIdList,
        courseId: this.examInfo.courseId,
        teacherId: this.examInfo.teacherId
      };
      console.log(e);

      if (!this.examInfo.startTime) {
        this.showCheckDialog = true;
        setTimeout(() => {
          this.showCheckDialog = false;
        }, 1000);
        return false;
      }
      if (!this.examInfo.endTime) {
        this.showCheckDialog = true;
        setTimeout(() => {
          this.showCheckDialog = false;
        }, 1000);
        return false;
      }
      if (
        !(
          this.check(this.examInfo.startTime) &&
          this.check(this.examInfo.endTime)
        )
      ) {
        this.showCheckDialog = true;
        setTimeout(() => {
          this.showCheckDialog = false;
        }, 1000);
        return false;
      }

      createExam(e).then(res => {
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
    },
    check(date) {
      const reg = /^((((19|20)[0-9][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((19|20)[0-9][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$/;
      return reg.test(date);
    }
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
