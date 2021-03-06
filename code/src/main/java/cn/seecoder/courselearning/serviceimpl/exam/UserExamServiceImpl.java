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
/**
*@ClassName: UserExamServiceImpl
*@Description:用户测试服务实现类
*@Version 3.0
*
*/

public class UserExamServiceImpl implements UserExamService {
    @Resource
    private UserExamMapper userExamMapper;

    @Override
    /**
     * @param userExamVO
     * @return cn.seecoder.courselearning.vo.ResultVO<cn.seecoder.courselearning.vo.exam.UserExamVO>
     * @describe:作答测试
     */
    public ResultVO<UserExamVO> setUpExam(UserExamVO userExamVO) {
        assert !userExamVO.getTrueAnswer().equals("");
        clearExamHistory(userExamVO.getUserId(), userExamVO.getExamId());
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
            else{
                ret.append("0");
            }
            if(i < userAnswer.size() - 1){
                ret.append("::");
            }
        }
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
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "测试提交成功", userExamVO);
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
    /**
     * @param flags 每道题的正误情况
     * @return int
     * @describe:计算成绩，其中多选题不全均按50%计算，最后转换为100分制
     */
    private int calScore(String flags){
        double ans;
        int res = 0;
        String[] tmp = flags.split("::");
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

    @Override
    /**
     * @param userId
     * @param examId
     * @return cn.seecoder.courselearning.vo.exam.UserExamVO
     * @describe:通过用户id和测试id获取测试结果
     */
    public UserExamVO getUserExam(Integer userId, Integer examId) {
        if(userExamMapper.selectByPrimaryKey(examId, userId) != null) {
//            System.out.println(userExamMapper.selectByPrimaryKey(examId, userId));
            return new UserExamVO(userExamMapper.selectByPrimaryKey(examId, userId));
        }
        else{
            return null;
        }
    }

//    public static void main(String[] args){
//        System.out.println(calScore("0::2::1::1::2::2::0"));
//        System.out.println(calScore("2::2::2::2::2::2::2"));
//        System.out.println(calScore("2::1::3::2::1::1::0"));
//        System.out.println(calScore("0::0::0::0::0::0::0"));
//    }

    private void clearExamHistory(Integer uid, Integer eid){
        if(userExamMapper.selectByPrimaryKey(eid, uid) != null){
            userExamMapper.deleteByPrimaryKey(eid, uid);
        }
    }
}
