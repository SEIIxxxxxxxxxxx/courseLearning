package cn.seecoder.courselearning.serviceimpl.exam;

import cn.seecoder.courselearning.mapperservice.exam.UserExamMapper;
import cn.seecoder.courselearning.po.exam.UserExam;
import cn.seecoder.courselearning.service.exam.UserExamService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserExamServiceImpl implements UserExamService {
    @Resource
    private UserExamMapper userExamMapper;

    @Override
    public ResultVO<UserExamVO> createUserExam(UserExamVO userExamVO) {
        UserExam userExam = new UserExam(userExamVO);
        System.out.println(userExam);
        if (userExamMapper.insert(userExam) > 0) {
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "题目创建成功", userExamVO);
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    @Override
    public ResultVO<UserExamVO> setUpExam(UserExamVO userExamVO) {
        List<String> userAnswer = userExamVO.splitUserAnswers(userExamVO.getUserAnswer());
        List<String> trueAnswer = userExamVO.splitTrueAnswers(userExamVO.getTrueAnswer());
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < userAnswer.size(); i++) {
            if (userAnswer.get(i) == trueAnswer.get(i)) {
                ret.append("1");
            }
            ret.append("0");
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        userExamVO.setTrueOrFalse(ret.toString());
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "测试提交成功", userExamVO);
    }
}
