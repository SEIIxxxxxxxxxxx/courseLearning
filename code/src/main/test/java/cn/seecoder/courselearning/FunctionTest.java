package cn.seecoder.courselearning;

import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.exam.ExamService;
import cn.seecoder.courselearning.service.exam.QuestionService;
import cn.seecoder.courselearning.service.order.VIPOrderService;
import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.QuestionVO;
import cn.seecoder.courselearning.vo.order.VIPOrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionTest {
    @Resource
    VIPOrderService vipOrderService;
    @Resource
    CourseService courseService;
    @Resource
    ExamService examService;
    @Resource
    QuestionService questionService;

    private final VIPOrderVO vipOrderVO1 = new VIPOrderVO(){{
        setType(1);
        setUserId(2);
    }};

    private final VIPOrderVO vipOrderVO2 = new VIPOrderVO(){{
        setType(3);
        setUserId(2);
    }};

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
        setQuestionIdList("1::2");
        setStartingTime(LocalDateTime.now().minusDays(1L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};
    private final ExamVO examVO2 = new ExamVO(){{
        setCourseId(1);
        setQuestionIdList("1::2");
        setStartingTime(LocalDateTime.now().plusDays(3L));
        setEndingTime(LocalDateTime.now().plusDays(1L));
        setTeacherId(1);
    }};

    @Test
    @Transactional
    public void vipTest() {
        int value1 = vipOrderService.createVipOrder(vipOrderVO1.getUserId(), vipOrderVO1.getType()).getCode();
        assertThat(value1, is(1));
        // 余额不足，购买失败
        int value2 = vipOrderService.createVipOrder(vipOrderVO2.getUserId(), vipOrderVO2.getType()).getCode();
        assertThat(value2, is(0));
        assertThat(courseService.getBoughtCourses(vipOrderVO1.getUserId()).size(), is(2));
    }

    @Test
    @Transactional
    public void examTest() {
        int value1 = questionService.createQuestion(questionVO1).getCode();
        assertThat(value1, is(1));
        int value2 = questionService.createQuestion(questionVO2).getCode();
        assertThat(value2, is(1));
        int value3 = questionService.createQuestion(questionVO3).getCode();
        assertThat(value3, is(1));
        int value4 = examService.createExam(examVO1).getCode();
        assertThat(value4, is(1));
        String msg = examService.createExam(examVO2).getMsg();
        assertThat(msg, is("测试时间错误"));
    }
}
