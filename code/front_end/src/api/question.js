import axios from "axios";
import { QUESTION_MODULE } from "@/api/_prefix";

/**
 * 创建一道题目 POST question/create
 * @returns
 */
export const createQuestion = question => {
  const { type, stem, option, analysis, answer, courseID } = question;
  return axios
    .post(`${QUESTION_MODULE}/create`, {
      type,
      stem,
      option,
      analysis,
      answer,
      courseID
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 获取全部题库题目
 */

export const getAllQuestions = () => {
  return axios.get(`${QUESTION_MODULE}/all`).then(res => {
    return res.data;
  });
};

/**
 * 获取指定courseID题库题目
 * @param {*} courseID
 */

export const getCourseQuestion = courseID => {
  return axios.get(`${QUESTION_MODULE}/cid/${courseID}`).then(res => {
    return res.data;
  });
};
