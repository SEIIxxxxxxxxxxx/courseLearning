package cn.seecoder.courselearning.po.exam;

import cn.seecoder.courselearning.vo.exam.UserExamVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserExam {
    private Integer userId;

    private Integer examId;

    private Integer score;

    //记录正确的答案
    private String trueAnswer;

    //记录学生的答案
    private String userAnswer;

    //记录正误情况，正为"1"，误为"0"
    private String trueOrFalse;

    public UserExam(UserExamVO userExamVO){
        this.examId = userExamVO.getExamId();
        this.userId = userExamVO.getUserId();
        this.score = userExamVO.getScore();
        this.trueAnswer = userExamVO.getTrueAnswer();
        this.userAnswer = userExamVO.getUserAnswer();
        this.trueOrFalse = userExamVO.getTrueOrFalse();
    }

    public String combineTrueAnswer(List<String> trueAnswers){
        StringBuilder ret = new StringBuilder();
        for(String ta:trueAnswers){
            ret.append(ta);
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }

    public String combineUserAnswer(List<String> userAnswers){
        StringBuilder ret = new StringBuilder();
        for(String ua:userAnswers){
            ret.append(ua);
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }

    public String combineTrueOrFalse(List<String> trueOrFalse){
        StringBuilder ret = new StringBuilder();
        for(String tof:trueOrFalse){
            ret.append(tof);
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }
}
