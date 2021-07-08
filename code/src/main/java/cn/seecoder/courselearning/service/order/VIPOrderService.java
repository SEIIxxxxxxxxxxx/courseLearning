package cn.seecoder.courselearning.service.order;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;

import java.util.List;

public interface VIPOrderService {
    // 根据用户id获取该用户的会员信息
    ResultVO<String> getVipEndTime(Integer uid);

    // 创建会员订单
    ResultVO<VIPOrderVO> createVipOrder(Integer userId, Integer type);

    // 判断会员身份
    ResultVO<Boolean> isVip(Integer uid);

    // 得到会员订单
    List<VIPOrderVO> getVipOrder(Integer userId);
}
