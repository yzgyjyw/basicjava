package com.jyw.lq.clone;

public class PrototypePattern {

    public static void main(String[] args) {

    }



}

class Student implements Cloneable{
    String name;
    int age;
    Teacher teacher;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = null;
        try{
            student = (Student) super.clone();
            Teacher teacher = (Teacher) student.teacher.clone();
            student.teacher = teacher;
        }catch(Exception e){

        }
        return student;
    }
}

class Teacher implements Cloneable{
    String name;
    int age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
