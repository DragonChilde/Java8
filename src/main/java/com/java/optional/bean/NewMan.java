package com.java.optional.bean;

import java.util.Optional;

/**
 * @author Lee
 * @create 2019/10/8 14:27
 */
public class NewMan {
    Optional<Teacher> teacher;

    public NewMan(Optional<Teacher> teacher) {
        this.teacher = teacher;
    }

    public NewMan() {
    }

    public Optional<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(Optional<Teacher> teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "NewMan{" +
                "teacher=" + teacher +
                '}';
    }
}
