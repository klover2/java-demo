package com.klover.jackson;

import example_code.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class XmlUtilTest {

    @Test
    void parse() {
        String xmlString = "<room>\n" +
                "    <name>天水一班</name>\n" +
                "    <no>1</no>\n" +
                "    <info>\n" +
                "        <address>天水</address>\n" +
                "        <number>5</number>\n" +
                "    </info>\n" +
                "    <students>\n" +
                "        <student>\n" +
                "            <name>张三</name>\n" +
                "            <age>18</age>\n" +
                "        </student>\n" +
                "        <student>\n" +
                "            <name>李四</name>\n" +
                "            <age>19</age>\n" +
                "        </student>\n" +
                "        <student>\n" +
                "            <name>王五</name>\n" +
                "            <age>19</age>\n" +
                "        </student>\n" +
                "    </students>\n" +
                "    <teacher>\n" +
                "        <name>王老师</name>\n" +
                "        <age>40</age>\n" +
                "    </teacher>\n" +
                "    <teacher>\n" +
                "        <name>曾老师</name>\n" +
                "        <age>45</age>\n" +
                "    </teacher>\n" +
                "</room>";

        Room parse = XmlUtil.parse(xmlString, Room.class);
        System.out.println(parse);
    }

    @Test
    void toXMLString() {
        Room room = new Room();
        room.setNo(1);
        room.setName("天水一班");
        Room.Info info = new Room.Info();
        info.setAddress("天水");
        info.setNumber(5);
        room.setInfo(info);
        ArrayList<Room.Teacher> teachers = new ArrayList<>();
        Room.Teacher teacher = new Room.Teacher();
        teacher.setAge(40);
        teacher.setName("王老师");
        teachers.add(teacher);
        Room.Teacher teacher2 = new Room.Teacher();
        teacher2.setAge(45);
        teacher2.setName("曾老师");
        teachers.add(teacher2);
        room.setTeachers(teachers);
        ArrayList<Room.Student> students = new ArrayList<>();
        Room.Student student = new Room.Student();
        student.setAge(18);
        student.setName("张三");
        students.add(student);
        Room.Student student2 = new Room.Student();
        student2.setAge(19);
        student2.setName("李四");
        students.add(student2);
        Room.Student student3 = new Room.Student();
        student3.setAge(19);
        student3.setName("王五");
        students.add(student3);
        room.setStudents(students);

        String xmlString = XmlUtil.toXMLString(room);
        System.out.println(xmlString);

//        xmlString = "<room>\n" +
//                "    <name>天水一班</name>\n" +
//                "    <no>1</no>\n" +
//                "    <info>\n" +
//                "        <address>天水</address>\n" +
//                "        <number>5</number>\n" +
//                "    </info>\n" +
//                "    <students>\n" +
//                "        <student>\n" +
//                "            <name>张三</name>\n" +
//                "            <age>18</age>\n" +
//                "        </student>\n" +
//                "        <student>\n" +
//                "            <name>李四</name>\n" +
//                "            <age>19</age>\n" +
//                "        </student>\n" +
//                "        <student>\n" +
//                "            <name>王五</name>\n" +
//                "            <age>19</age>\n" +
//                "        </student>\n" +
//                "    </students>\n" +
//                "    <teacher>\n" +
//                "        <name>王老师</name>\n" +
//                "        <age>40</age>\n" +
//                "    </teacher>\n" +
//                "    <teacher>\n" +
//                "        <name>曾老师</name>\n" +
//                "        <age>45</age>\n" +
//                "    </teacher>\n" +
//                "</room>";
    }
}