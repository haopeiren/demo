package hpr;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

/**
 * @author haopeiren
 * @since 2020/4/2
 */
public class ComparatorTest
{
    public static void main(String[] args)
    {
        List<Person> pList = new ArrayList<>();
        pList.add(new Person("zhangsan", 13));
        pList.add(new Person("lisi", 14));
        pList.add(new Person("wangwu", 12));

        Comparator<Person> personComparator = new Comparator<Person>()
        {
            @Override
            public int compare(Person o1, Person o2)
            {
                //1 调换前后的位置  升序
                //-1  降序
                //o1, o2
                return o1.getAge() - o2.getAge();
            }
        };
        Collections.sort(pList, personComparator);
        System.out.println(pList);
        HashMap map = new HashMap();
        HashSet set = new HashSet();
    }

    @Getter
    @Setter
    @Data
    static class Person{
        private String name;

        private int age;

        public Person()
        {

        }

        public Person(String name, int age)
        {
            this.name = name;
            this.age = age;
        }
    }
}
