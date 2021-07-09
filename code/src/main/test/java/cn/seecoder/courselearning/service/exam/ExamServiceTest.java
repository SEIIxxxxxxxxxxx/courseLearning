package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.exam.ExamVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamServiceTest {
    @Resource
    ExamService examService;

    // 进行中测试
    private final ExamVO examVO1 = new ExamVO(){{
        setCourseId(1);
        setQuestionIdList("1::2");
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};
    private final ExamVO examVO2 = new ExamVO(){{
        setCourseId(2);
        setQuestionIdList("1::2::3");
        setStartingTime(LocalDateTime.now().minusHours(3L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};
    // 已结束测试
    private final ExamVO examVO3 = new ExamVO(){{
        setCourseId(2);
        setQuestionIdList("3::2");
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().minusHours(2L));
        setTeacherId(1);
    }};
    // 未开始测试
    private final ExamVO examVO4 = new ExamVO(){{
        setCourseId(2);
        setQuestionIdList("2");
        setStartingTime(LocalDateTime.now().plusDays(3L));
        setEndingTime(LocalDateTime.now().plusWeeks(1L));
        setTeacherId(1);
    }};

    @Test
    @Transactional
    public void createExam() {
        examService.createExam(examVO1);
        List<ExamVO> examVOS = examService.getExam(examVO1.getCourseId());
        assertThat(examVOS.get(examVOS.size() - 1).getQuestionIdList(), is("1::2"));
    }

    @Test
    @Transactional
    public void getExam() {
        List<ExamVO> examVOS = examService.getExam(1);
        examService.createExam(examVO4);
        examService.createExam(examVO1);
        List<ExamVO> examVOS1 = examService.getExam(1);
        assertThat(examVOS1.size() - examVOS.size(), is(1));

    }

    @Test
    @Transactional
    public void getContinueExam() {
        List<ExamVO> examVOS = examService.getContinueExam(2);
        examService.createExam(examVO2);
        examService.createExam(examVO3);
        examService.createExam(examVO4);
        List<ExamVO> examVOS1 = examService.getContinueExam(2);
        assertThat(examVOS1.size() - examVOS.size(), is(1));
    }

    @Test
    @Transactional
    public void getOverExam() {
        List<ExamVO> examVOS = examService.getContinueExam(2);
        examService.createExam(examVO2);
        examService.createExam(examVO3);
        examService.createExam(examVO4);
        List<ExamVO> examVOS1 = examService.getContinueExam(2);
        assertThat(examVOS1.size() - examVOS.size(), is(1));
    }
}