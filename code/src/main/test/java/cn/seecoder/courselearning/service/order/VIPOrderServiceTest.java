package cn.seecoder.courselearning.service.order;

import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VIPOrderServiceTest {
    @Resource
    VIPOrderService vipOrderService;
    @Resource
    UserService userService;

    private Calendar calendar = new GregorianCalendar();

    private final VIPOrderVO vipOrderVO1 = new VIPOrderVO(){{
        setType(1);
        setUserId(2);
    }};

    private final VIPOrderVO vipOrderVO2 = new VIPOrderVO(){{
        setType(2);
        setUserId(2);
    }};

    private final VIPOrderVO vipOrderVO3 = new VIPOrderVO(){{
        setType(3);
        setUserId(2);
    }};

    @Test
    @Transactional
    public void getVipEndTime() {
        userService.increaseBalance(vipOrderVO3.getUserId(), 1000);
        vipOrderService.createVipOrder(vipOrderVO3.getUserId(), vipOrderVO3.getType());
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 365);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 不排除刚好有1s误差
        assertThat(vipOrderService.getVipEndTime(vipOrderVO3.getUserId()).getData(), is(formatter.format(calendar.getTime())));
        // 会员期内继续购买会员
        vipOrderService.createVipOrder(vipOrderVO2.getUserId(), vipOrderVO2.getType());
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.DATE, 30);
        assertThat(vipOrderService.getVipEndTime(vipOrderVO2.getUserId()).getData(), is(formatter.format(calendar.getTime())));
    }

    @Test
    @Transactional
    public void createVipOrder() {
        vipOrderService.createVipOrder(vipOrderVO1.getUserId(), vipOrderVO1.getType());
        assertThat(vipOrderService.isVip(vipOrderVO1.getUserId()).getData(), is(true));
    }

    @Test
    @Transactional
    public void isVip() {
        // 余额不足
        vipOrderService.createVipOrder(vipOrderVO3.getUserId(), vipOrderVO3.getType());
        assertThat(vipOrderService.isVip(vipOrderVO3.getUserId()).getData(), is(false));
        userService.increaseBalance(vipOrderVO3.getUserId(), 1000);
        vipOrderService.createVipOrder(vipOrderVO3.getUserId(), vipOrderVO3.getType());
        assertThat(vipOrderService.isVip(vipOrderVO3.getUserId()).getData(), is(true));
    }

    @Test
    @Transactional
    public void getVipOrder() {
        List<VIPOrderVO> vipOrderVOList = vipOrderService.getVipOrder(vipOrderVO2.getUserId());
        vipOrderService.createVipOrder(vipOrderVO2.getUserId(), vipOrderVO2.getType());
        // 余额不足
        vipOrderService.createVipOrder(vipOrderVO3.getUserId(), vipOrderVO3.getType());
        List<VIPOrderVO> vipOrderVOLists = vipOrderService.getVipOrder(vipOrderVO2.getUserId());
        assertThat(vipOrderVOLists.size() - vipOrderVOList.size(), is(1));
        assertThat(vipOrderVOLists.get(vipOrderVOLists.size() - 1).getType(), is(2));
    }
}