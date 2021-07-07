package cn.seecoder.courselearning.po.order;

import cn.seecoder.courselearning.vo.order.CourseOrderVO;
import lombok.NonNull;

import java.util.Date;

public class CourseOrder {
    private Integer id;

    private Integer type;

    private Integer origin;

    private Integer cost;

    private Integer courseId;

    private String courseName;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    private Integer userId;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType(){return type;}

    public void setType(int type){this.type=type;}

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime(){return startTime;}

    public void setStartTime(Date startTime){this.startTime=startTime;}

    public Date getEndTime(){return endTime;}

    public void setEndTime(Date endTime){this.endTime=endTime;}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CourseOrder() {
    }

    public CourseOrder(@NonNull CourseOrderVO orderVO){
        this.id = orderVO.getId();
        this.type=orderVO.getType();
        this.origin = orderVO.getOrigin();
        this.cost = orderVO.getCost();
        this.courseId = orderVO.getCourseId();
        this.courseName = orderVO.getCourseName();
        this.createTime = orderVO.getCreateTime()==null ? new Date():orderVO.getCreateTime();
        this.startTime = orderVO.getStartTime()==null ? new Date() : orderVO.getStartTime();
        this.endTime = orderVO.getEndTime()==null ? new Date() : orderVO.getEndTime();
        this.userId = orderVO.getUserId();
        this.status = orderVO.getStatus();
    }
}