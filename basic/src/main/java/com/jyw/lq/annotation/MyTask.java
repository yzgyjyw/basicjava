package com.jyw.lq.annotation;
import java.util.Arrays;

public class MyTask {
    public static void main(String[] args) {
        Arrays.stream(Task.class.getMethods()).forEach(method->{
            TODOAnnotation annotation = method.getAnnotation(TODOAnnotation.class);
            if(annotation!=null &&
                    annotation.author().equals("jyw") &&
                    annotation.priority().equals(TODOAnnotation.Priority.high)){
                try {
                    method.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
