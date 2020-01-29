package hpr;

/**
 * java bean
 * @author haopeiren
 * @since 2020/1/29
 */
public class Student
{
    //private protect public friendly

    private int id;

    private int score;

    private String name;

    private int age;

    public static void main(String[] args)
    {
        Student lihaitao = new Student();
        lihaitao.setName("lihaitao");
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public void setName(String name)
    {
        this.setAge(1);
        this.name = name;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
