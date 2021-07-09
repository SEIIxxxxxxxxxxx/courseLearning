package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.UserExam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserExamMapperTest {
    @Resource
    private UserExamMapper userExamMapper;

    private final UserExam userExam1 = new UserExam() {{
        setUserId(2);
        setUserAnswer("A::B::C");
        setTrueAnswer("C::ABCD::D");
        setTrueOrFalse("0::1::0");
        setScore(16);
        setExamId(222);
    }};

    private final UserExam userExam2 = new UserExam() {{
        setUserId(2);
        setUserAnswer("B::D::D");
        setTrueAnswer("A::CD::D");
        setTrueOrFalse("0::1::1");
        setScore(50);
        setExamId(22);
    }};

    @Test
    @Transactional
    public void insert() {
        int value = userExamMapper.insert(userExam1);
        assertThat(value, is(1));
    }

    @Test
    @Transactional
    public void selectByPrimaryKey() {
        userExamMapper.insert(userExam1);
        userExamMapper.insert(userExam2);
        UserExam userExam = userExamMapper.selectByPrimaryKey(userExam1.getExamId(), userExam1.getUserId());
        assertThat(userExam.getScore(), is(16));
        userExam = userExamMapper.selectByPrimaryKey(userExam2.getExamId(), userExam2.getUserId());
        assertThat(userExam.getScore(), is(50));
    }
}