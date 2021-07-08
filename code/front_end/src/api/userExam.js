import axios from "axios";
import { USER_EXAM_MODULE } from "./_prefix";

/**
 * 用户提交测试 POST exam/setUp
 * @param  {*} userExam
 * @returns
 */
export const createUserExam = userExam => {
  const {
    userId,
    examId,
    score,
    trueAnswer,
    userAnswer,
    trueOrFalse
  } = userExam;
  return axios
    .post(`${USER_EXAM_MODULE}/setUp`, {
      userId,
      examId,
      score,
      trueAnswer,
      userAnswer,
      trueOrFalse
    })
    .then(res => {
      return res.data;
    });
};
/**
 * 用户提交测试 POST exam/setUp
 * @param  {*} userId
 * @param  {*} examId
 * @returns
 */

export const getUserExam = (userId, examId) => {
  return axios
    .get(`${USER_EXAM_MODULE}/getUserExam`, {
      params: {
        userId,
        examId
      }
    })
    .then(res => res.data);
};
