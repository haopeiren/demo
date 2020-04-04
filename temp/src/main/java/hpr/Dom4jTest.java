package hpr;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * @author haopeiren
 * @since 2020/4/4
 */
public class Dom4jTest
{
    //Dom Sax
    //dom  加载xml所有内容到内存中，优点，速度快   缺点，xml文件比较大时 耗内存
    //sax  不会加载所有内容到内存， 优点，省内存，缺点，效率比dom慢
    public static void main(String[] args) throws Exception
    {
        String filePath = "D:\\work\\apache-maven-3.3.3\\conf\\settings.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element root = document.getRootElement();
        for (int i = 0; i < root.attributes().size(); i++)
        {
            Attribute currentAttribute = root.attribute(i);
            System.out.println(currentAttribute.getName() + " : " + currentAttribute.getValue());
        }
        root.getParent();
        List<Element> elementList = root.elements();


    }
}
