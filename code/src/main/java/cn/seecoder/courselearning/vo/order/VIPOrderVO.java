package cn.seecoder.courselearning.vo.order;

import cn.seecoder.courselearning.po.order.VIPOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VIPOrderVO {
    private Integer id;

    private Integer userId;

    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public VIPOrderVO() {
    }

    public VIPOrderVO(VIPOrder vipOrder){
        this.id = vipOrder.getId();
        this.userId = vipOrder.getUserId();
        this.type = vipOrder.getType();
        this.createTime = vipOrder.cal_createTime(vipOrder.getEndTime(), vipOrder.getType());
        this.endTime = vipOrder.getEndTime();
    }
}
