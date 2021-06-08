package cn.seecoder.courselearning.mapperservice.course;

import org.apache.ibatis.annotations.Param;

public interface CourseLikesMapper {
//    TODO 完善了接口的定义
    int deleteByPrimaryKey(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int insert(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int count(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int countLikesOfCourse(@Param("courseId") Integer courseId);
}
