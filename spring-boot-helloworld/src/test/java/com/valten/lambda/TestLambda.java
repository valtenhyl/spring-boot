package com.valten.lambda;

import com.alibaba.fastjson.JSONObject;
import com.valten.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className MaxAndMin
 * @package com.valten.lambda
 * @author huangyuanli
 * @date 2020/7/13 16:44
 **/
public class TestLambda {

    private static final List<Student> students = new ArrayList<>();

    static {
        Student s1 = new Student(1L, "肖战", 14, "北京市丰台区");
        Student s2 = new Student(2L, "王一博", 15, "北京市西城区");
        Student s3 = new Student(3L, "杨紫", 16, "北京市东城区");
        Student s4 = new Student(4L, "李现", 14, "北京市丰台区");
        Student s5 = new Student(5L, "肖战", 17, "广东省东莞市");
        Student s6 = new Student(5L, "肖战", 17, "广东省东莞市");
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.add(s6);
    }

    // 求集合中元素的最小值
    @Test
    void testMin() {
        Student min = students.stream().min(Comparator.comparingInt(Student::getAge)).orElse(new Student(0L, null, 0, null));
        System.out.println(min.toString());
    }

    // 集合skip，跳过前n个元素
    @Test
    void testSkip() {
        List<String> list = Arrays.asList("333", "222", "111");
        list.stream().skip(2).forEach(System.out::println);
        System.out.println(list);
    }

    // 集合reduce,将集合中每个元素聚合成一条数据
    @Test
    void testReduce() {
        List<String> list = Arrays.asList("欢", "迎", "你");
        String appendStr = list.stream().reduce("北京", (a, b) -> a + b);
        System.out.println(appendStr);
    }

    // 返回前n个元素
    @Test
    void testLimit() {
        List<String> list = Arrays.asList("333", "222", "111");
        list.stream().limit(2).forEach(System.out::println);
    }

    // 集合去重（引用对象）
    @Test
    void testDistinct() {
        //引用对象的去重，引用对象要实现hashCode和equal方法，否则去重无效
        students.stream().distinct().forEach(System.out::println);
    }

    // 集合过滤
    @Test
    void testFilter() {
        //筛选年龄大于15岁的学生
        List <Student> studentList1 = students.stream().filter(s -> s.getAge()>15).collect(Collectors.toList());
        studentList1.forEach(System.out::println);
        //筛选住在浙江省的学生
        List<Student> studentList2 = students.stream().filter(s -> "浙江".equals(s.getAddress())).collect(Collectors.toList());
        studentList2.forEach(System.out::println);
    }

    // 处理集合得到新的集合
    @Test
    void testList() {
        List<JSONObject> stuList = students.stream().collect(ArrayList::new, (list, item) -> {
            // 可以写过滤、字段筛选等各种逻辑
            JSONObject obj = new JSONObject();
            obj.put("name", item.getName());
            obj.put("age", item.getAge());
            obj.put("address", item.getAddress());

            list.add(obj);
        }, List::addAll);
        System.out.println(stuList);
    }

}
