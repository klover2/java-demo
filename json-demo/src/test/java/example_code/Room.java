package example_code;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * StudentXml
 *
 * @author klover
 * @date 2024/3/18 12:01
 */
@Data
@JacksonXmlRootElement(localName = "room")
public class Room {
    /**
     * 房间名称
     */
    @JacksonXmlProperty(localName = "name")
    private String name;
    /**
     * 房间号
     */
    @JacksonXmlProperty(localName = "no")
    private Integer no;

    /**
     * 其他信息
     */
    @JacksonXmlProperty(localName = "info")
    private Info info;

    /**
     * 学生
     */
    @JacksonXmlElementWrapper(localName = "students")
    @JacksonXmlProperty(localName = "student")
    private List<Student> students;

    /**
     * 老师
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "teacher")
    private List<Teacher> teachers;

    @Data
    public static class Student {
        @JacksonXmlProperty(localName = "name")
        private String name;
        @JacksonXmlProperty(localName = "age")
        private Integer age;
    }

    @Data
    public static class Teacher {
        @JacksonXmlProperty(localName = "name")
        private String name;
        @JacksonXmlProperty(localName = "age")
        private Integer age;
    }

    @Data
    public static class Info {
        @JacksonXmlProperty(localName = "address")
        private String address;
        /**
         * 人数
         */
        @JacksonXmlProperty(localName = "number")
        private Integer number;
    }
}
