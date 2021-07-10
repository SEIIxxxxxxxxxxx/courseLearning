package cn.seecoder.courselearning.serviceimpl.exam;

import cn.seecoder.courselearning.mapperservice.exam.QuestionMapper;
import cn.seecoder.courselearning.po.exam.Question;
import cn.seecoder.courselearning.service.exam.QuestionService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.exam.QuestionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    @Override
    /**
     * @param questionVO
     * @return cn.seecoder.courselearning.vo.ResultVO<cn.seecoder.courselearning.vo.exam.QuestionVO>
     * @describe:新建题目
     */
    public ResultVO<QuestionVO> createQuestion(QuestionVO questionVO){
        // assert questionVO != null;
        Question question = new Question(questionVO);
        System.out.println(question);
        if(questionMapper.insert(question) > 0){
            return new ResultVO<>(Constant.REQUEST_SUCCESS, "题目创建成功", questionVO);
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    @Override
    public ResultVO<QuestionVO> getQuestionById(Integer id) {
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "题目获取成功", new QuestionVO(questionMapper.selectByPrimaryKey(id))) ;
    }

    @Override
    /**
     * @param
     * @return java.util.List<cn.seecoder.courselearning.vo.exam.QuestionVO>
     * @describe:获取全部题目
     */
    public List<QuestionVO> getAllQuestion(){
        List<QuestionVO> ret = new ArrayList<>();
        List<Question> questions = questionMapper.selectAll();
        for(Question question: questions){
            ret.add(new QuestionVO(question));
        }
        return ret;
    }

    @Override
    /**
     * @param courseID
     * @return java.util.List<cn.seecoder.courselearning.vo.exam.QuestionVO>
     * @describe:获取课程全部题目
     */
    public List<QuestionVO> getQuestion(Integer courseID){
        assert courseID > 0;
        List<QuestionVO> ret = new ArrayList<>();
        List<Question> questions = questionMapper.selectAll();
        for(Question question: questions){
            if(question.getCourseID().equals(courseID)){
                ret.add(new QuestionVO(question));
            }
        }
        return ret;
    }
}
