package com.java.optional.bean;

/**
 * @author Lee
 * @create 2019/10/8 14:11
 */
public class Man {
    Teacher teacher;

    public Man(Teacher teacher) {
        this.teacher = teacher;
    }

    public Man() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Man{" +
                "teacher=" + teacher +
                '}';
    }
}
