package hpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haopeiren
 * @since 2020/1/29
 */
public class FanxingTest2<K, V>
{
    private K key;
    private V value;

    public V getValue()
    {
        return value;
    }

    public K getKey()
    {
        return key;
    }

    public static void main(String[] args)
    {
        Object obj = null;
        FanxingTest2 test2 = new FanxingTest2();
        obj = test2.getValue();
//        String str = test2.getValue();   错误类型
        FanxingTest2<String, String> test3 = new FanxingTest2<String, String>();
        FanxingTest2<String, String> test4 = new FanxingTest2<>();
        String str = test3.getValue();
//        int a = test3.getValue();

        List list = new ArrayList();
        list.add(1);
        list.add("zhangsan");
//        String ss = list.get(11);

        int index = 10;
        Object result = list.get(index);

        Map map = new HashMap();
        map.put("name", "zhangsan");
        map.put(1, 2);
        Object objMap = map.get("name");

        List<String> strList = new ArrayList<>();
//        strList.add(1); 类型错误
        strList.add("zhangsan");
        String strResult = strList.get(index);

        Map<String, String> strMap = new HashMap<>();
//        strMap.put(1, 1);
        strMap.put("name", "zhangsan");
//        int aa = strMap.get("name");
        String name = strMap.get("name");
    }
}
