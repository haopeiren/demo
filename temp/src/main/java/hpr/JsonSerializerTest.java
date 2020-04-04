package hpr;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author haopeiren
 * @since 2020/4/4
 */
public class JsonSerializerTest
{
    public static void main(String[] args)
    {
        //Serializer  序列化  DeSerializer 反序列化
        Person person = new Person();
        person.setFirstName("wangwu");
        person.setAge(14);
        person.setScores(Arrays.asList(11, 12, 13, 14));
        person.setPersonList(Arrays.asList(new Person()));
        // alibaba fastjson,  jackson
        //fastjson
        //对象转字符串
        System.out.println(JSONObject.toJSONString(person, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty));
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", "13");
        System.out.println(JSONObject.toJSONString(map));
    }

    @Getter
    @Setter
    static class Person
    {
        private String firstName;

        private Integer age;

        private List<Integer> scores;

        private List<Person> personList;

        private Student student;

        private Person person;
    }

    static class Student
    {

    }
}
