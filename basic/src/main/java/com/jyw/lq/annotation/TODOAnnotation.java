package com.jyw.lq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//元注解,定义注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
//元注解,定义注解的目标
@Target(ElementType.METHOD)
public @interface TODOAnnotation {
    //注解中的属性只能是枚举或者string类型或者基本类型
    enum Priority {
        low, medium, high
    }

    String author() default "jyw";

    Priority priority() default Priority.medium;
}