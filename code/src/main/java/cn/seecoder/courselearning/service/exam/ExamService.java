package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;

import java.util.List;

public interface ExamService {
    //创建测试
    ResultVO<ExamVO> createExam(ExamVO examVO);
    //获取所有测试
    List<ExamVO> getAllExam();
    //获取测试列表
    List<ExamVO> getExam(Integer courseId);
    //作答测试
    ResultVO<ExamVO> answerExam(ExamVO examVO);
}
