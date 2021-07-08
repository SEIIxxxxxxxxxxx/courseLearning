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
  return axios.get(`${QUESTION_MODULE}/getByCourseId/${courseID}`).then(res => {
    return res.data;
  });
};

/**
 * 获取指定ID题库题目
 * @param {*} id
 */

export const getQuestionById = id => {
  return axios.get(`${QUESTION_MODULE}/getById/${id}`).then(res => {
    return res.data;
  });
};

/**
 * 得到课程对应全部题目
 * @param {*} courseId
 * @returns
 */

export const getAvailableQuestionsForCourseId = courseId => {
  return axios.get(`${QUESTION_MODULE}/getByCourseId/${courseId}`).then(res => {
    return res.data;
  });
};
