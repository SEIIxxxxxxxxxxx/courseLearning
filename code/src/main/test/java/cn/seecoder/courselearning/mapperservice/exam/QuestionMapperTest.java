package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMapperTest {
    @Resource
    QuestionMapper questionMapper;

    private final Question question1 = new Question(){{
        setCourseID(1);
        setStem("今天能写完吗");
        setAnalysis("加油");
        setType("单选题");
        setOptions("写不完了::躺平了::卷翻天::泪目");
        setAnswer("D");
    }};

    private final Question question2 = new Question(){{
       setCourseID(2);
       setStem("南京大学有哪几个校区");
       setType("多选题");
       setAnalysis("球球不要搬三次宿舍");
       setOptions("仙林::鼓楼::浦口::苏州");
       setAnswer("ABCD");
    }};

    @Test
    @Transactional
    public void insert() {
        int value1 = questionMapper.insert(question1);
        int value2 = questionMapper.insert(question2);
        assertThat(value1, is(1));
        assertThat(value2, is(1));
    }

    @Test
    @Transactional
    public void selectByCourseId() {
        questionMapper.insert(question1);
        questionMapper.insert(question2);
        List<Question> questionList = questionMapper.selectByCourseId(question1.getCourseID());
        assertThat(questionList.get(questionList.size() - 1).getAnswer(), is("D"));
    }
}