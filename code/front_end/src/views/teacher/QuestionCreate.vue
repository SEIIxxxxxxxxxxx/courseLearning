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
      题目创建成功！
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
    <v-container class="pl-16 pr-16">
      <form class="pa-12 grey lighten-5 mt-8">
        <v-textarea
          v-model="questionInfo.stem"
          label="题干"
          clearable
          :counter="200"
          :rules="rules.AreaRules"
        ></v-textarea>
        <v-select
          :items="types"
          v-model="questionInfo.type"
          label="题型选择"
        ></v-select>
        <v-slider
          v-show="
            questionInfo.type === '单选题' || questionInfo.type === '多选题'
          "
          v-model="option_num"
          label="选项个数"
          step="1"
          max="10"
          min="1"
          thumb-label
          ticks
          @change="createOptions"
        ></v-slider>
        <ul
          v-show="
            questionInfo.type === '单选题' || questionInfo.type === '多选题'
          "
        >
          <li v-for="(op, index) in options" :key="op.id">
            {{ op.id }}
            <v-text-field
              v-model="options[index].option"
              label="选项内容"
              :counter="50"
              :rules="rules.OptionRules"
            >
            </v-text-field>
          </li>
        </ul>
        <v-textarea
          v-model="questionInfo.analysis"
          label="解析"
          :counter="200"
          clearable
        ></v-textarea>
        <div v-show="questionInfo.type === '单选题'">
          {{ "正确答案" }}
          <v-radio-group v-model="questionInfo.answer">
            <v-radio
              v-for="n in options"
              :key="n.id"
              :label="`${n.id} ： ${n.option}`"
            ></v-radio>
          </v-radio-group>
        </div>
        <div v-show="questionInfo.type === '多选题'">
          {{ "正确答案" }}
          <ul>
            <li v-for="(op, index) in options" :key="op.id">
              <v-checkbox
                :label="`${op.id} ： ${op.option}`"
                v-model="answers[index].isAnswer"
                color="success"
              ></v-checkbox>
            </li>
          </ul>
        </div>
        <v-textarea
          v-show="questionInfo.type === '问答题'"
          label="答案"
          v-model="questionInfo.answer"
          clearable
          :counter="200"
          :rules="rules.AreaRules"
        ></v-textarea>
        <v-text-field
          v-model="questionInfo.courseID"
          label="所属课程ID"
        ></v-text-field>
        <v-btn class="ml-0 mt-8 info" v-show="checkSubmit()" @click="submit">
          确认
        </v-btn>
      </form>
    </v-container>
  </div>
</template>

<script>
import { createQuestion } from "@/api/question";

export default {
  inject: ["reload"],
  name: "QuestionCreate",

  data() {
    return {
      questionInfo: {
        stem: "",
        type: "单选题",
        str_option: "",
        answer: "",
        analysis: "",
        courseID: 0
      },
      option_num: 4,
      options: [],
      answers: [],
      types: ["单选题", "多选题", "问答题"],
      dialog: false,
      showSuccessDialog: false,
      showFailDialog: false,
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

  mounted() {
    this.createOptions();
  },

  methods: {
    checkSubmit() {
      if (this.questionInfo.stem === "") {
        return false;
      }
      for (let i = 0; i < this.option_num; i++) {
        if (this.options[i].option === "") {
          return false;
        }
      }
      if (this.questionInfo.type !== "多选题") {
        if (this.questionInfo.answer === "") {
          return false;
        }
      } else {
        let r = false;
        for (let i = 0; i < this.option_num; i++) {
          if (this.answers[i].isAnswer) {
            if (r) {
              return true;
            } else {
              r = true;
            }
          }
        }
        return false;
      }
      return true;
    },
    createOptions() {
      let ret = [];
      for (let i = 0; i < this.option_num; i++) {
        ret.push({ id: String.fromCharCode(i + 65), option: "" });
        this.answers.push({ id: String.fromCharCode(i + 65), isAnswer: false });
      }
      this.options = ret;
    },
    formatOptions() {
      for (let i = 0; i < this.option_num; i++) {
        this.questionInfo.str_option += this.options[i].option;
        this.questionInfo.str_option += "::";
        if (this.answers[i].isAnswer) {
          this.questionInfo.answer += this.answers[i].id;
        }
      }
      this.questionInfo.str_option = this.questionInfo.str_option.substr(
        0,
        this.questionInfo.str_option.length - 2
      );
    },
    formatSingle() {
      this.questionInfo.answer = String.fromCharCode(
        Number(this.questionInfo.answer) + 65
      );
    },
    submit() {
      if (this.questionInfo.type !== "问答题") {
        this.formatOptions();
      }
      if (this.questionInfo.type === "单选题") {
        this.formatSingle();
      }
      const q = {
        type: this.questionInfo.type,
        stem: this.questionInfo.stem,
        option: this.questionInfo.str_option,
        answer: this.questionInfo.answer,
        analysis: this.questionInfo.analysis,
        courseID: this.questionInfo.courseID
      };
      console.log(q);
      createQuestion(q).then(res => {
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
