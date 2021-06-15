package cn.seecoder.courselearning.vo.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.seecoder.courselearning.po.exam.Exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExamVO {
    private Integer id;

    private String questionIdList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endingTime;

    private Integer courseId;

    private Integer teacherId;

    public ExamVO(Exam exam){
        this.id = exam.getId();
        this.startingTime = exam.getStartingTime();
        this.endingTime = exam.getEndingTime();
        this.questionIdList = exam.getQuestionIdList();
        this.courseId = exam.getCourseId();
        this.teacherId = exam.getTeacherId();
    }

    private List<String> splitQuestions(String question){
        List<String> ret = new ArrayList<>();
        String[] x = question.split("::");
        Collections.addAll(ret, x);
        return ret;
    }
}
