import axios from "axios";
import { ORDER_MODULE, VIP_MODULE } from "./_prefix";

/**
 * 生成课程订单（可用于购买课程或赠送课程） POST course_order/create
 * @returns
 * @param uid
 * @param courseId
 * @param type
 */
export const createOrder = (uid, courseId, type) => {
  return axios
    .post(`${ORDER_MODULE}/create?uid=${uid}&courseId=${courseId}&type=${type}`)
    .then(res => {
      return res.data;
    });
};

/**
 * 更新订单状态 POST course_order/update
 * @param {*} payload
 * @returns
 */
export const updateOrder = payload => {
  const { orderId, orderStatus } = payload;
  return axios
    .post(
      `${ORDER_MODULE}/update?orderId=${orderId}&orderStatus=${orderStatus}`
    )
    .then(res => {
      return res.data;
    });
};

/**
 * 获取某用户的所有订单 GET course_order/uid/{uid}
 * @param {*} uid
 * @returns
 */
export const getOrdersByUser = uid => {
  return axios.get(`${ORDER_MODULE}/uid/${uid}`).then(res => {
    return res.data;
  });
};

/**
 * 获取某一订单信息
 * @param orderId
 * @returns {Promise<*>}
 */
export const getOrderById = orderId => {
  return axios.get(`${ORDER_MODULE}/orderId/${orderId}`).then(res => {
    return res.data;
  });
};

/**
 * 使用优惠券
 * @param order
 * @param couponId
 * @returns {Promise<AxiosResponse<any>>}
 */
export const useCoupon = (order, couponId) => {
  return axios
    .post(`${ORDER_MODULE}/use_coupon?couponId=${couponId}`, order)
    .then(res => res.data);
};

/**
 * 取消使用优惠券
 * @param order
 * @param couponId
 * @returns {Promise<AxiosResponse<any>>}
 */
export const cancelCoupon = (order, couponId) => {
  return axios
    .post(`${ORDER_MODULE}/cancel_coupon?couponId=${couponId}`, order)
    .then(res => res.data);
};

export const payOrder = (orderId, duration, rentCost) => {
  return axios
    .post(
      `${ORDER_MODULE}/pay?orderId=${orderId}&duration=${duration}&rentCost=${rentCost}`
    )
    .then(res => res.data);
};

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
