import axios from "axios";
import { VIP_MODULE } from "./_prefix";

/**
 * 生成会员订单（可用于购买会员） POST VIP/create
 * @param uid
 * @param type
 * @returns
 */
export const createVipOrder = (uid, type) => {
  return axios
    .post(`${VIP_MODULE}/create?uid=${uid}&type=${type}`)
    .then(res => {
      return res.data;
    });
};

/**
 * 获取用户会员到期时间
 * @returns {Promise<*>}
 * @param userId
 */
export const getVipEndTime = userId => {
  return axios.get(`${VIP_MODULE}/getEndTime/${userId}`).then(res => {
    return res.data.data;
  });
};

/**
 * 获取用户会员订单
 * @param userId
 */
export const getVipOrder = userId => {
  return axios.get(`${VIP_MODULE}/getVipOrder/${userId}`).then(res => {
    return res.data;
  });
};
