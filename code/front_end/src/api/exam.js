import axios from "axios";
import { EXAM_MODULE } from "./_prefix";

/**
 * 创建一项测试 POST exam/create
 * @param  {*} exam
 * @returns
 */
export const createExam = exam => {
  const {
    startingTime,
    endingTime,
    questionIdList,
    courseId,
    teacherId
  } = exam;
  return axios
    .post(`${EXAM_MODULE}/create`, {
      startingTime,
      endingTime,
      questionIdList,
      courseId,
      teacherId
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 根据测试号获取测试  GET exam/getExamById
 * @param {*} eid
 * @returns
 */

export const getExamById = eid => {
  return axios
    .get(`${EXAM_MODULE}/getExamById`, {
      params: {
        eid
      }
    })
    .then(res => res.data);
};

/**
 * 得到课程对应进行中测试
 * @param {*} courseId
 * @returns
 */

export const getContinueExam = courseId => {
  return axios
    .get(`${EXAM_MODULE}/getContinueExam`, {
      params: {
        courseId
      }
    })
    .then(res => res.data);
};

/**
 * 得到课程对应已结束测试
 * @param {*} courseId
 * @returns
 */

export const getOverExam = courseId => {
  return axios
    .get(`${EXAM_MODULE}/getOverExam`, {
      params: {
        courseId
      }
    })
    .then(res => res.data);
};
