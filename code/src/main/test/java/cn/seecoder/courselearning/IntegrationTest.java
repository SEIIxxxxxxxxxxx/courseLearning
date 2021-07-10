package cn.seecoder.courselearning;

import cn.seecoder.courselearning.mapperservice.order.CourseOrderMapper;
import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.exam.ExamService;
import cn.seecoder.courselearning.service.exam.QuestionService;
import cn.seecoder.courselearning.service.exam.UserExamService;
import cn.seecoder.courselearning.service.order.CourseOrderService;
import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.QuestionVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
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
public class IntegrationTest {
    @Resource
    CourseOrderService courseOrderService;
    @Resource
    ExamService examService;
    @Resource
    QuestionService questionService;
    @Resource
    UserExamService userExamService;

    private final QuestionVO questionVO1 = new QuestionVO(){{
        setCourseID(1);
        setType("多选题");
        setStem("明天组长可能几点起床？");
        setOption("十点::八点::不起了::中午");
        setAnswer("ACD");
        setAnalysis("好饿好累好困好热");
    }};
    private final QuestionVO questionVO2 = new QuestionVO(){{
        setCourseID(1);
        setType("单选题");
        setStem("哪家是火锅店？");
        setOption("盛江山::杨国福::川西集市::南京大学第九食堂");
        setAnalysis("组长什么时候能请客？");
        setAnswer("C");
    }};
    private final QuestionVO questionVO3 = new QuestionVO(){{
        setCourseID(1);
        setType("问答题");
        setStem("hylxjj人美心善!!!");
        setOption("");
        setAnalysis("zyqyxr99!!!");
        setAnswer("yama酱ssy99!!!");
    }};

    private final ExamVO examVO1 = new ExamVO(){{
        setCourseId(1);
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};

    private final UserExamVO userExamVO1 = new UserExamVO() {{
        setUserId(2);
        setUserAnswer("A::B::67yyds");
        setTrueAnswer("ACD::C::诸事顺遂");
        setTrueOrFalse("-1");
    }};

    @Test
    @Transactional
    public void integrationTest() {
        // 创建三类题
        int value1 = questionService.createQuestion(questionVO1).getCode();
        assertThat(value1, is(1));
        int value2 = questionService.createQuestion(questionVO2).getCode();
        assertThat(value2, is(1));
        int value3 = questionService.createQuestion(questionVO3).getCode();
        assertThat(value3, is(1));
        // 创建测试
        List<QuestionVO> questionVOList = questionService.getQuestion(questionVO1.getCourseID());
        int index = questionVOList.get(questionVOList.size() - 1).getId();
        examVO1.setQuestionIdList((index - 2) + "::" +(index - 1) + "::" + index);
        int value4 = examService.createExam(examVO1).getCode();
        assertThat(value4, is(1));
        // 租用课程
        int value5 = courseOrderService.createCourseOrder(examVO1.getCourseId(), 2, 2).getCode();
        assertThat(value5, is(1));
        // 完成测试
        List<ExamVO> examVOS = examService.getExam(examVO1.getCourseId());
        userExamVO1.setExamId(examVOS.get((examVOS.size() - 1)).getId());
        int value6 = userExamService.setUpExam(userExamVO1).getCode();
        assertThat(value6, is(1));
        // 查看成绩
        int score = userExamService.getUserExam(userExamVO1.getUserId(), userExamVO1.getExamId()).getScore();
        assertThat(score, is(16));
    }
}
