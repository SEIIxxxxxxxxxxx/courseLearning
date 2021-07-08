package cn.seecoder.courselearning.serviceimpl.order;

import cn.seecoder.courselearning.mapperservice.order.VIPOrderMapper;
import cn.seecoder.courselearning.po.order.VIPOrder;
import cn.seecoder.courselearning.service.order.VIPOrderService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;
import cn.seecoder.courselearning.vo.user.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VIPOrderServiceImpl implements VIPOrderService {
    @Resource
    private VIPOrderMapper vipOrderMapper;

    @Resource
    private UserService userService;

    @Override
    public ResultVO<String> getVipEndTime(Integer uid) {
        List<VIPOrder> data = vipOrderMapper.selectByUserId(uid);
        Date cur = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = "";
        for (VIPOrder datum : data) {
            if (datum.getEndTime().compareTo(cur) > 0) {
                endTime = formatter.format(datum.getEndTime());
//                System.out.println(endTime);
                cur = datum.getEndTime();
            }
        }
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "查找完成", endTime);
    }

    private Date getVipHistory(Integer uid){
        List<VIPOrder> data = vipOrderMapper.selectByUserId(uid);
        Date cur = new Date();
        for (VIPOrder datum : data) {
            if (datum.getEndTime().compareTo(cur) > 0) {
                cur = datum.getEndTime();
            }
        }
        return cur;
    }

    @Override
    public ResultVO<VIPOrderVO> createVipOrder(Integer userId, Integer type) {
        int[] costs = new int[]{5, 20, 180};
        int cost = costs[type - 1];
        UserVO user = userService.getUser(userId);
        if(user.getBalance() >= cost){
            userService.decreaseBalance(userId, cost);
            VIPOrderVO tmp = new VIPOrderVO();
            tmp.setCreateTime(this.getVipHistory(userId));
            tmp.setUserId(userId);
            tmp.setType(type);
            VIPOrder vipOrder = new VIPOrder(tmp);
            System.out.println(vipOrder);
            if(vipOrderMapper.insert(vipOrder)>0){
                return new ResultVO<>(Constant.REQUEST_SUCCESS, "订单创建成功", tmp);
            }
            return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
        }
        else{
            return new ResultVO<>(Constant.REQUEST_FAIL, "用户余额不足");
        }
    }

    @Override
    public ResultVO<Boolean> isVip(Integer uid) {
        List<VIPOrder> data = vipOrderMapper.selectByUserId(uid);
        Date cur = new Date();
        for (VIPOrder datum : data) {
            if (datum.getEndTime().compareTo(cur) > 0) {
                return new ResultVO<>(Constant.REQUEST_SUCCESS, "欢迎您，会员", true);
            }
        }
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "您还不是会员", false);
    }

    @Override
    public List<VIPOrderVO> getVipOrder(Integer userId) {
        List<VIPOrder> data = vipOrderMapper.selectByUserId(userId);
        List<VIPOrderVO> ret = new ArrayList<>();
        for (VIPOrder tmp : data) {
            ret.add(new VIPOrderVO(tmp));
        }
        return ret;
    }
}
