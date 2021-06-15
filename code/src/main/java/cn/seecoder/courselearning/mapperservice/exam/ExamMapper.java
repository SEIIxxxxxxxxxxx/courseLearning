package cn.seecoder.courselearning.mapperservice.exam;

import cn.seecoder.courselearning.po.exam.Exam;
import java.util.List;

public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam exam);

    Exam selectByPrimaryKey(Integer id);

    List<Exam> selectAll();

    int updateByPrimaryKey(Exam record);

    // 根据课程Id查找测试，返回测试列表
    List<Exam> selectByCourseId(Integer courseId);

    List<Exam> selectByTeacherId(Integer teacherId);
}
