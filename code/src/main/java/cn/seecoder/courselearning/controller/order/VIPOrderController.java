package cn.seecoder.courselearning.controller.order;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/VIP")
public class VIPOrderController {

    /**
     * 创建会员订单
     */
    @PostMapping("/create")
    public ResultVO<VIPOrderVO> createVIPOrder(@RequestParam Integer uid, @RequestParam Integer type){
        return null;
    }

    /**
     * 获取会员到期时间
     */
    //TODO: 如果会员已过期或无会员充值信息则返回空字符串
    @GetMapping("/getEndTime/{userId}")
    public ResultVO<String> getVipEndTime(@PathVariable Integer userId){
        return null;
    }
}
