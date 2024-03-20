package com.klover.serialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klover.jackson.JsonUtil;
import lombok.Data;

/**
 * Demo
 *
 * @author klover
 * @date 2024/3/20 17:11
 */
public class Demo {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("张三");
        student.setCardNumber("1111111111111111");
        student.setAddress("beijing");
        System.out.println(JsonUtil.toJSONString(student));
        // ======> {"name":"张三","cardNumber":"111111******1111","address":"***"}
    }

    @Data
    public static class Student{
        private String name;

        @JsonSerialize(using = CardNumberSerialize.class)
        private String cardNumber;

        @JsonSerialize(using = CompleteDesensitizationSerialize.class)
        private String address;
    }
}
