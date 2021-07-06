package cn.seecoder.courselearning.controller.exam;

import cn.seecoder.courselearning.service.exam.ExamService;
import cn.seecoder.courselearning.service.exam.UserExamService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamService examService;
    UserExamService userExamService;

    /**
     * 创建测试
     */
    @PostMapping("/create")
    public ResultVO<ExamVO> createExam(@RequestBody ExamVO exam){
        return examService.createExam(exam);
    }


    /**
     * 根据课程id查找对应测试
     *  @param cid 课程id
     */
    @PostMapping("/cid/{cid}")
    public List<ExamVO> getCourseExam(@PathVariable Integer cid){
        return examService.getExam(cid);
    }

    /**
     * 返回所有测试
     */
    @PostMapping("/all")
    public List<ExamVO> getExam(){
        return examService.getAllExam();
    }

    /**
     * 返回对应测试号的测试
     */
    @GetMapping("getExamById")
    public ExamVO getExamById(@RequestParam Integer eid){
        return examService.getExamById(eid);
    }



//    /**
//     * 作答测试
//     */
//    @PostMapping("/answer")
//    public ResultVO<ExamVO> answerExam(@RequestBody ExamVO exam){
//        return examService.answerExam(exam);
//    }

    /**
     * 提交测试
     */
    @PostMapping("/setUp")
    public ResultVO<UserExamVO> setUpExam(@RequestBody UserExamVO userExam){
        return userExamService.setUpExam(userExam);
    }

    /**
     * 得到进行中测试
     */
    @GetMapping("/getContinueExam")
    public List<ExamVO> getContinueExam(@RequestParam Integer courseId){
        return examService.getContinueExam(courseId);
    }

    /**
     * 得到已结束测试
     */
    @GetMapping("/getOverExam")
    public List<ExamVO> getOverExam(@RequestParam Integer courseId){
//        System.out.println(examService.getOverExam(courseId));
        return examService.getOverExam(courseId);
    }
}
