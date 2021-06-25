package cn.seecoder.courselearning.po.order;

import cn.seecoder.courselearning.vo.order.VIPOrderVO;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class VIPOrder {
    private Integer id;

    private Integer userId;

    private Integer type;

//    存储会员到期时间
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public VIPOrder(){

    }

    public VIPOrder(VIPOrderVO vipOrderVO){
        this.id = vipOrderVO.getId();
        this.type = vipOrderVO.getType();
        this.userId = vipOrderVO.getUserId();
        this.endTime = cal_endTime(vipOrderVO.getCreateTime(), vipOrderVO.getType());
    }

    public Date cal_createTime(Date endTime, Integer type){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(endTime);
        switch (type){
            case 1:
                calendar.add(Calendar.DATE,-1);
                break;
            case 2:
                calendar.add(Calendar.DATE, -30);
                break;
            case 3:
                calendar.add(Calendar.DATE, -365);
                break;
            default:
                break;
        }
        endTime = calendar.getTime();
        return endTime;
    }

    public Date cal_endTime(Date createTime, Integer type){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(createTime);
        switch (type){
            case 1:
                calendar.add(Calendar.DATE,1);
                break;
            case 2:
                calendar.add(Calendar.DATE, 30);
                break;
            case 3:
                calendar.add(Calendar.DATE, 365);
                break;
            default:
                break;
        }
        createTime = calendar.getTime();
        return createTime;
    }
}
