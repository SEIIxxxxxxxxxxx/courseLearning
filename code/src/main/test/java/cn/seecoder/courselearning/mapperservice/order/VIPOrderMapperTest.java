package cn.seecoder.courselearning.mapperservice.order;

import cn.seecoder.courselearning.po.order.VIPOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VIPOrderMapperTest {
    @Resource
    VIPOrderMapper vipOrderMapper;

    private VIPOrder vipOrder = new VIPOrder(){{
        setType(1);
        setUserId(2);
        setEndTime(new Date());
    }};

    @Test
    @Transactional
    public void insert() {
        int val = vipOrderMapper.insert(vipOrder);
        assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void selectByUserId() {
        List<VIPOrder> vipOrdersList = vipOrderMapper.selectByUserId(2);
        assertThat(vipOrdersList.size(), is(1));
    }
}