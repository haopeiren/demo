package hpr;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haopeiren
 * @since 2020/4/4
 */
public class JsonDeSerializerTest
{
    public static void main(String[] args) throws ClassNotFoundException, IOException
    {
        String personStr = "{\"name\": \"zhangsan\",\"age\": \"13\"}";
        Person person = JSONObject.parseObject(personStr, Person.class);
        System.out.println(person.getFirstName());
        System.out.println(person.getAge());

        Student student = JSONObject.parseObject(personStr, Student.class);
        System.out.println(student.getName());
        System.out.println(student.getAge());

        System.out.println("---------------json--------------------");
        //jackson
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(person));

        Student student1 = objectMapper.readValue(personStr, Student.class);
        System.out.println(student1.getName() + " " + student1.getAge());

        System.out.println("---------------array----------------");
        List<Person> list = new ArrayList<>();
        System.out.println(JSONObject.toJSONString(list));
        System.out.println(objectMapper.writeValueAsString(list));

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student1);
        studentList.add(student1);
        String array = objectMapper.writeValueAsString(studentList);
        System.out.println(JSONObject.toJSONString(studentList));
        System.out.println(array);

        System.out.println("--------------deserialize----------");

        List<Student> sList = JSONArray.parseArray(array, Student.class);
        System.out.println(sList);

    }
    @Getter
    @Setter
    static class Person
    {
        private String firstName;

        private Integer age;

        private List<Integer> scores;

        private List<JsonSerializerTest.Person> personList;

        private JsonSerializerTest.Student student;

        private JsonSerializerTest.Person person;
    }

    @Setter
    @Getter
    @ToString
    static class Student
    {
        private String name;

        private Integer age;

    }

}
