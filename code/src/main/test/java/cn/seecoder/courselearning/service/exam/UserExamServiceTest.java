package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserExamServiceTest {
    @Resource
    private UserExamService userExamService;
    @Resource
    private ExamService examService;

    // 进行中测试
    private final ExamVO examVO1 = new ExamVO(){{
        setCourseId(2);
        setQuestionIdList("1::2::3");
        setStartingTime(LocalDateTime.now().minusHours(3L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};
    // 已结束测试
    private final ExamVO examVO2 = new ExamVO(){{
        setCourseId(2);
        setQuestionIdList("3::2");
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().minusHours(2L));
        setTeacherId(1);
    }};

    private final UserExamVO userExamVO1 = new UserExamVO() {{
       setUserId(2);
       setUserAnswer("A::B::C");
       setTrueAnswer("C::ABCD::D");
       setTrueOrFalse("-1");
    }};

    @Test
    @Transactional
    public void setUpExam() {
        examService.createExam(examVO1);
        List<ExamVO> examVOS = examService.getExam(examVO1.getCourseId());
        userExamVO1.setExamId(examVOS.get((examVOS.size() - 1)).getId());
        UserExamVO userExamVO = userExamService.setUpExam(userExamVO1).getData();
        assertThat(userExamVO.getUserAnswer(), is("A::B::C"));
        assertThat(userExamVO.getScore(), is(16));
    }

    // 未参加考试
    @Test
    @Transactional
    public void getUserExam1() {
        examService.createExam(examVO2);
        List<ExamVO> examVOS = examService.getExam(examVO2.getCourseId());
        UserExamVO userExamVO = userExamService.getUserExam(2, examVOS.get((examVOS.size() - 1)).getId());
        assertThat(userExamVO, is(nullValue()));
    }

    @Test
    public void getUserExam2() {
        examService.createExam(examVO1);
        List<ExamVO> examVOS = examService.getExam(examVO1.getCourseId());
        userExamVO1.setExamId(examVOS.get((examVOS.size() - 1)).getId());
        userExamService.setUpExam(userExamVO1);
        List<ExamVO> examVOS1 = examService.getExam(examVO1.getCourseId());
        UserExamVO userExamVO = userExamService.getUserExam(userExamVO1.getUserId(), examVOS1.get(examVOS1.size() - 1).getId());
        assertThat(userExamVO.getScore(), is(16));
    }
}