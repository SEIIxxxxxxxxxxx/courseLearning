package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.exam.QuestionVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {
    @Resource
    private QuestionService questionService;
    // 多选题
    private final QuestionVO questionVO1 = new QuestionVO(){{
        setCourseID(1);
        setType("多选题");
        setStem("哪几个部分要检查？");
        setOption("过程::功能::代码::文档");
        setAnswer("ABCD");
        setAnalysis("来不及了，但是67yyds！");
    }};
    private final QuestionVO questionVO2 = new QuestionVO(){{
        setCourseID(2);
        setType("多选题");
        setStem("明天组长可能几点起床？");
        setOption("十点::八点::不起了::中午");
        setAnswer("ACD");
        setAnalysis("好饿好累好困好热");
    }};
    // 单选题
    private final QuestionVO questionVO3 = new QuestionVO(){{
        setCourseID(1);
        setType("单选题");
        setStem("哪家是火锅店？");
        setOption("盛江山::杨国福::川西集市::南京大学第九食堂");
        setAnalysis("组长什么时候能请客？");
        setAnswer("C");
    }};
    // 问答题
    private final QuestionVO questionVO4 = new QuestionVO(){{
        setCourseID(1);
        setType("问答题");
        setStem("hylxjj人美心善!!!");
        setOption("");
        setAnalysis("zyqyxr99!!!");
        setAnswer("yama酱ssy99!!!");
    }};

    @Test
    @Transactional
    public void getQuestion() {
        List<QuestionVO> questionVOList =  questionService.getQuestion(1);
        questionService.createQuestion(questionVO1);
        questionService.createQuestion(questionVO2);
        List<QuestionVO> questionVOLists =  questionService.getQuestion(1);
        assertThat(questionVOLists.size() - questionVOList.size(), is(1));
    }

    @Test
    @Transactional
    public void createQuestion() {
        questionService.createQuestion(questionVO1);
        QuestionVO q1 = questionService.getQuestion(1).get(questionService.getQuestion(1).size() - 1);
        assertThat(q1.getAnswer(), is("ABCD"));
        questionService.createQuestion(questionVO3);
        QuestionVO q3 = questionService.getQuestion(1).get(questionService.getQuestion(1).size() - 1);
        assertThat(q3.getStem(), is("哪家是火锅店？"));
        assertThat(q3.getAnswer(), is("C"));
        questionService.createQuestion(questionVO4);
        QuestionVO q4 = questionService.getQuestion(1).get(questionService.getQuestion(1).size() - 1);
        assertThat(q4.getOption(), is(""));
        assertThat(q4.getType(), is("问答题"));
    }
}