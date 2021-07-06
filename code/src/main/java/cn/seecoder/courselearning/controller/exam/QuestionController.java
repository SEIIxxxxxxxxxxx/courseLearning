package cn.seecoder.courselearning.controller.exam;

import cn.seecoder.courselearning.service.exam.QuestionService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.QuestionVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionService questionService;

    /**
     * 创建题目
     */
    @PostMapping("/create")
    public ResultVO<QuestionVO> createQuestion(@RequestBody QuestionVO question){
        return questionService.createQuestion(question);
    }

    /**
     * 根据课程id查找对应题目
     *  @param courseId 课程id
     */
    @GetMapping("/getByCourseId/{courseId}")
    public List<QuestionVO> getCourseQuestion(@PathVariable String courseId){
        return questionService.getQuestion(Integer.parseInt(courseId));
    }

    /**
     * 根据题目id查找对应题目
     *  @param id 题目id
     */
    @GetMapping("/getById/{id}")
    public ResultVO<QuestionVO> getQuestionById(@PathVariable String id){
        return questionService.getQuestionById(Integer.parseInt(id));
    }

    /**
     * 返回题库中全部题目
     */
    @GetMapping("/all")
    public List<QuestionVO> getQuestion(){
        return questionService.getAllQuestion();
    }
}
