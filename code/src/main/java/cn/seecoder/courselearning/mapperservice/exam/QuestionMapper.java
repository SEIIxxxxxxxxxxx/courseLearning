package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.Question;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question question);

    Question selectByPrimaryKey(Integer id);

    List<Question> selectAll();

    List<Question> selectByCourseId(Integer courseId);
}
