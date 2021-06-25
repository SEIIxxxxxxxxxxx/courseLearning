package cn.seecoder.courselearning.mapperservice.order;

import cn.seecoder.courselearning.po.order.VIPOrder;

import java.util.List;

public interface VIPOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VIPOrder record);

    VIPOrder selectByPrimaryKey(Integer id);

    List<VIPOrder> selectByUserId(Integer userId);
}
