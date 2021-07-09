package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.UserExam;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserExamMapper {
    int insert(UserExam record);

    int update(UserExam record);

    UserExam selectByPrimaryKey(@Param(value = "examId") Integer examId, @Param(value = "userId")  Integer userId);

    int deleteByPrimaryKey(@Param(value = "examId") Integer examId, @Param(value = "userId")  Integer userId);
}
