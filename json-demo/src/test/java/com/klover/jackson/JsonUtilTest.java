package com.klover.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import example_code.Student;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void toJSONString() {
        Student student = new Student();
        student.setName("张三");
        student.setAge(10);
        String jsonString = JsonUtil.toJSONString(student);
        System.out.println(jsonString);
    }

    @Test
    void convert() {
        Student student = new Student();
        student.setName("张三");
        student.setAge(10);
        Map<String, Object> convert = JsonUtil.convert(student);
        System.out.println(convert);
    }

    @Test
    void parse() {
        String s = "{\"name\":\"张三\",\"age\":10}";
        // 第一种
        Student parse = JsonUtil.parse(s, Student.class);
        System.out.println(parse);
        // 第二种
        Student parse1 = JsonUtil.parse(s, new TypeReference<Student>() {
        });
        System.out.println(parse1);
        // 第三种
        JavaType javaType = JsonUtil.getCollectionType(Student.class);
        Object parse2 = JsonUtil.parse(s, javaType);
        System.out.println(parse2);
    }
}