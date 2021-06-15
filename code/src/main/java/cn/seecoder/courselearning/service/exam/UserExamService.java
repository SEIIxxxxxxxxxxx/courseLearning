package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;

public interface UserExamService {
    //创建测试信息
    ResultVO<UserExamVO> createUserExam(UserExamVO userexamVO);
    //提交测试
    ResultVO<UserExamVO> setUpExam(UserExamVO userexamVO);
}
