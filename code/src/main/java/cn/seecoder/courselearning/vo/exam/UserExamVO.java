package cn.seecoder.courselearning.vo.exam;

import cn.seecoder.courselearning.po.exam.UserExam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class UserExamVO {
    private Integer userId;

    private Integer examId;

    private Integer score;

    private String trueAnswer;

    private String userAnswer;

    private String trueOrFalse;

    public UserExamVO(UserExam userExam){
        this.userId = userExam.getUserId();
        this.examId = userExam.getExamId();
        this.score = userExam.getScore();
        this.trueAnswer = userExam.getTrueAnswer();
        this.userAnswer = userExam.getUserAnswer();
        this.trueOrFalse = userExam.getTrueOrFalse();
    }

    public List<String> splitAnswers(String trueAnswer){
        List<String> ret = new ArrayList<>();
        String[] x = trueAnswer.split("::");
        Collections.addAll(ret, x);
        return ret;
    }
}
