package cn.seecoder.courselearning.vo.exam;

import cn.seecoder.courselearning.po.exam.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionVO {
    private Integer id;

    private String type;

    private String stem;

    private String option;

    private String analysis;

    private String answer;

    private Integer courseID;

    public QuestionVO(Question question){
        this.id = question.getId();
        this.type = question.getType();
        this.stem = question.getStem();
        this.option = question.getOptions();
        this.answer = question.getAnswer();
        this.analysis = question.getAnalysis();
        this.courseID = question.getCourseID();
    }

    private List<String> splitOptions(String option){
        List<String> ret = new ArrayList<>();
        String[] x = option.split("::");
        Collections.addAll(ret, x);
        return ret;
    }
}
