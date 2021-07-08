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
    //根据测试号获取测试
    ExamVO getExamById(Integer eid);
    //获取进行中测试
    List<ExamVO> getContinueExam(Integer courseId);
    //获取已结束测试
    List<ExamVO> getOverExam(Integer courseId);
}
