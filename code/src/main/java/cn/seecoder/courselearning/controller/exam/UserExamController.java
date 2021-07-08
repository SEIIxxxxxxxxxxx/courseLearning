package cn.seecoder.courselearning.controller.exam;

import cn.seecoder.courselearning.service.exam.UserExamService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userExam")

public class UserExamController {
    @Resource
    UserExamService userExamService;
    /**
     * 提交测试
     */
    @PostMapping("/setUp")
    public ResultVO<UserExamVO> setUpExam(@RequestBody UserExamVO userExam){
        return userExamService.setUpExam(userExam);
    }

    /**
     * 得到用户测试
     */
    @GetMapping("/getUserExam")
    public UserExamVO getUserExam(@RequestParam Integer userId,Integer examId){
        return userExamService.getUserExam(userId,examId);
    }
}
