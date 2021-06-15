package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.UserExam;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserExamMapper {
    int insert(UserExam record);

    int update(UserExam record);

    UserExam selectByExamIdAndUserId(@Param(value = "examId") Integer examId, @Param(value = "userId")  Integer userId);

    List<UserExam>selectByUserId(Integer userId);

    int deleteByExamIdAndUserId(@Param(value = "examId") Integer examId, @Param(value = "userId")  Integer userId);
}
