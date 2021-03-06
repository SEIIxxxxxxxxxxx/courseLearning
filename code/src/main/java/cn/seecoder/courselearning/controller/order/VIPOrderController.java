package cn.seecoder.courselearning.controller.order;

import cn.seecoder.courselearning.service.order.VIPOrderService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/VIP")
public class VIPOrderController {
    @Resource
    private VIPOrderService vipOrderService;

    /**
     * 创建会员订单
     */
    @PostMapping("/create")
    public ResultVO<VIPOrderVO> createVIPOrder(@RequestParam Integer uid, @RequestParam Integer type){
        return vipOrderService.createVipOrder(uid, type);
    }

    /**
     * 获取会员到期时间
     */
    // 如果会员已过期或无会员充值信息则返回空字符串
    @GetMapping("/getEndTime/{userId}")
    public ResultVO<String> getVipEndTime(@PathVariable Integer userId){
        return vipOrderService.getVipEndTime(userId);
    }

    /**
     * 判断会员身份
     */
    @GetMapping("/getVIP/{userId}")
    public ResultVO<Boolean> checkVIP(@PathVariable Integer userId){
        return vipOrderService.isVip(userId);
    }

    /**
     * 得到会员订单
     */
    @GetMapping("/getVipOrder/{userId}")
    public List<VIPOrderVO> createVIPOrder(@PathVariable Integer userId){
        return vipOrderService.getVipOrder(userId);
    }
}
