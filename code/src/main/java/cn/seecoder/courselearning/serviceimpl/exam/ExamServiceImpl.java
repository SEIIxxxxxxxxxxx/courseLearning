package cn.seecoder.courselearning.serviceimpl.exam;

import cn.seecoder.courselearning.mapperservice.exam.ExamMapper;
import cn.seecoder.courselearning.po.exam.Exam;
import cn.seecoder.courselearning.service.exam.ExamService;
import cn.seecoder.courselearning.service.exam.UserExamService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.ExamVO;
import cn.seecoder.courselearning.vo.exam.UserExamVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
/**
*@ClassName: ExamServiceImpl
*@Description:
*@Version 3.0
*
*/

public class ExamServiceImpl implements ExamService {
    @Resource
    private ExamMapper examMapper;

    @Override
    /**
     * @param examVO
     * @return cn.seecoder.courselearning.vo.ResultVO<cn.seecoder.courselearning.vo.exam.ExamVO>
     * @describe:新建测试
     */
    public ResultVO<ExamVO> createExam(ExamVO examVO){
        // assert examVO.getEndingTime().isAfter(examVO.getStartingTime());
        if(examVO.getEndingTime().isBefore(examVO.getStartingTime())||examVO.getEndingTime().isBefore(LocalDateTime.now().minusMinutes(1L))){
            return new ResultVO<>(Constant.REQUEST_FAIL, "测试时间错误");
        }
        Exam exam = new Exam(examVO);
        System.out.println(exam);
        if(examMapper.insert(exam) > 0){
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "测试创建成功", examVO);
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    @Override
    public List<ExamVO> getAllExam(){
        List<ExamVO> ret = new ArrayList<>();
        List<Exam> exams = examMapper.selectAll();
        for(Exam exam: exams){
            ret.add(new ExamVO(exam));
        }
        return ret;
    }

    @Override
    /**
     * @param courseId
     * @return java.util.List<cn.seecoder.courselearning.vo.exam.ExamVO>
     * @describe:返回课程全部测试
     */
    public List<ExamVO> getExam(Integer courseId) {
        List<ExamVO> ret = new ArrayList<>();
        List<Exam> exams = examMapper.selectAll();
        for(Exam exam: exams){
            if(exam.getCourseId().equals(courseId)){
                ret.add(new ExamVO(exam));
            }
        }
        return ret;
    }


    @Override
    public ExamVO getExamById(Integer eid) {
        return new ExamVO(examMapper.selectByPrimaryKey(eid));
    }

    @Override
    /**
     * @param courseId
     * @return java.util.List<cn.seecoder.courselearning.vo.exam.ExamVO>
     * @describe:获取进行中测试
     */
    public List<ExamVO> getContinueExam(Integer courseId) {
        List<ExamVO> ret = new ArrayList<>();
        List<Exam> exams = examMapper.selectAll();
        for(Exam exam: exams){
            if(exam.getCourseId().equals(courseId) && exam.getEndingTime().isAfter(LocalDateTime.now()) && exam.getStartingTime().isBefore(LocalDateTime.now())){
                ret.add(new ExamVO(exam));
            }
        }
        return ret;
    }

    @Override
    /**
     * @param courseId
     * @return java.util.List<cn.seecoder.courselearning.vo.exam.ExamVO>
     * @describe:获取已结束测试
     */
    public List<ExamVO> getOverExam(Integer courseId) {
        List<ExamVO> ret = new ArrayList<>();
        List<Exam> exams = examMapper.selectAll();
        for(Exam exam: exams){
            if(exam.getCourseId().equals(courseId) && exam.getEndingTime().isBefore(LocalDateTime.now())){
                ret.add(new ExamVO(exam));
            }
        }
        return ret;
    }
}
