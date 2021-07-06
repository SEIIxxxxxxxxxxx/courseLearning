package cn.seecoder.courselearning.service.exam;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;

public interface UserExamService {
    //提交测试
    ResultVO<UserExamVO> setUpExam(UserExamVO userexamVO);
}
