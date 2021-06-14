package cn.seecoder.courselearning.po.exam;

import cn.seecoder.courselearning.vo.exam.QuestionVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Question {
    private Integer id;

    private String type;

    private String stem;

    private String options;

    private String analysis;

    private String answer;

    private Integer courseID;

//    public Integer getQuestionID(){ return this.id; }
//
//    public void setQuestionID(Integer questionID){ this.id = questionID; }
//
//    public Integer getCourseID(){ return this.courseID; }
//
//    public void setCourseID(Integer courseID){ this.courseID = courseID; }
//
//    public String getType() {
//        return this.type;
//    }
//
//    public void setType(String type) {
//        this.type = type == null ? null : type.trim();
//    }
//
//    public String getStem() {
//        return stem;
//    }
//
//    public void setStem(String stem) {
//        this.stem = stem == null ? null : stem.trim();
//    }
//
//    public String getOption() {
//        return this.option;
//    }
//
//    public void setOption(String option) {
//        this.option = option;
//    }
//
//    public String getAnalysis() {
//        return this.analysis;
//    }
//
//    public void setAnalysis(String analysis) {
//        this.analysis = analysis == null ? null : analysis.trim();
//    }
//
//    public String getAnswer() {
//        return this.answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }

    public Question(QuestionVO question){
        this.id = question.getId();
        this.type = question.getType();
        this.stem = question.getStem();
        this.options = question.getOption();
        this.answer = question.getAnswer();
        this.analysis = question.getAnalysis();
        this.courseID = question.getCourseID();
    }

    public String combineOptions(List<String> options){
        StringBuilder ret = new StringBuilder();
        for(String op:options){
            ret.append(op);
            ret.append("::");
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }
}
