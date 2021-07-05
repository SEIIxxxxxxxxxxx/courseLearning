package cn.seecoder.courselearning.vo.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.seecoder.courselearning.po.exam.Exam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class ExamVO {
    private Integer id;

    private String questionIdList;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endingTime;

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
