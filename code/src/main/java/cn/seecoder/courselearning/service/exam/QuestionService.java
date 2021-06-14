package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.QuestionVO;

import java.util.List;

public interface QuestionService {
    List<QuestionVO> getAllQuestion();

    List<QuestionVO> getQuestion(Integer courseID);

    ResultVO<QuestionVO> createQuestion(QuestionVO questionVO);
}
