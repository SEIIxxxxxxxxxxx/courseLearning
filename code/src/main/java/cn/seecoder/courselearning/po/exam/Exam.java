package cn.seecoder.courselearning.po.exam;

import cn.seecoder.courselearning.vo.exam.ExamVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Exam {
    private Integer id;

    private String questionIdList;

    private Date startingTime;

    private Date endingTime;

    private Integer courseId;

    private Integer teacherId;

    public Exam(ExamVO exam){
        this.id = exam.getId();
        this.questionIdList = exam.getQuestionIdList();
        this.startingTime = exam.getStartingTime();
        this.endingTime = exam.getEndingTime();
        this.courseId = exam.getCourseId();
        this.teacherId = exam.getTeacherId();
    }

    public String combineQuestion(List<Integer> questions){
        StringBuilder ret = new StringBuilder();
        for(Integer ques:questions){
            ret.append(ques);
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }
}
