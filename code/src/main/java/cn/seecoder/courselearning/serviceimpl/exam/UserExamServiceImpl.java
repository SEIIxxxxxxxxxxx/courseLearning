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
    public ResultVO<UserExamVO> setUpExam(UserExamVO userExamVO) {
        List<String> userAnswer = userExamVO.splitUserAnswers(userExamVO.getUserAnswer());
        List<String> trueAnswer = userExamVO.splitTrueAnswers(userExamVO.getTrueAnswer());
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < userAnswer.size(); i++) {
            if (userAnswer.get(i).equals(trueAnswer.get(i))) {
                ret.append("2");
            }
            else if(defineMulti(userAnswer.get(i), trueAnswer.get(i))){
                ret.append("1");
            }
            ret.append("0");
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        userExamVO.setTrueOrFalse(ret.toString());
        int score = calScore(userExamVO.getTrueOrFalse());
        if(score != -1){
            userExamVO.setScore(score);
        }
        else{
            return new ResultVO<>(Constant.REQUEST_FAIL, "分数暂时无法计算");
        }

        UserExam userExam = new UserExam(userExamVO);
        System.out.println(userExam);
        if (userExamMapper.insert(userExam) > 0) {
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "题目创建成功", userExamVO);
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    private boolean defineMulti(String userAnswer, String trueAnswer){
        String[] tmp = trueAnswer.split("");
        for(String s : tmp){
            if(userAnswer.contains(s)){
                return true;
            }
        }
        return false;
    }

    private int calScore(String flags){
        double ans;
        int res = 0;
        String[] tmp = flags.split("");
        for (String s : tmp) {
            if(Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 2){
                res += Integer.parseInt(s);
            }
            else{
                return -1;
            }
        }
        ans = res / (tmp.length * 2.0) * 100.0;
        return (int) ans;
    }

//    public static void main(String[] args){
//        System.out.println(calScore("0211220"));
//        System.out.println(calScore("2222222"));
//        System.out.println(calScore("2132110"));
//        System.out.println(calScore("0000000"));
//    }
}
