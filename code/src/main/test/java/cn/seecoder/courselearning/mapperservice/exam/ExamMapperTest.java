package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.Exam;
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
public class ExamMapperTest {
    @Resource
    private ExamMapper examMapper;

    // 进行中测试
    private final Exam exam1 = new Exam(){{
        setCourseId(2);
        setQuestionIdList("1::2::3");
        setStartingTime(LocalDateTime.now().minusHours(3L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};
    // 已结束测试
    private final Exam exam2 = new Exam(){{
        setCourseId(2);
        setQuestionIdList("3::2");
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().minusHours(2L));
        setTeacherId(1);
    }};

    @Test
    @Transactional
    public void insert() {
        int value1 = examMapper.insert(exam1);
        int value2 = examMapper.insert(exam2);
        assertThat(value1, is(1));
        assertThat(value2, is(1));
    }

    @Test
    @Transactional
    public void selectByCourseId() {
        List<Exam> exams = examMapper.selectByCourseId(exam1.getCourseId());
        examMapper.insert(exam1);
        examMapper.insert(exam2);
        List<Exam> newExams = examMapper.selectByCourseId(exam1.getCourseId());
        assertThat(newExams.size() - exams.size(), is(2));
    }

    @Test
    @Transactional
    public void selectByTeacherId() {
        List<Exam> exams = examMapper.selectByTeacherId(exam1.getTeacherId());
        examMapper.insert(exam1);
        examMapper.insert(exam2);
        List<Exam> newExams = examMapper.selectByTeacherId(exam1.getTeacherId());
        assertThat(newExams.size() - exams.size(), is(2));
    }
}