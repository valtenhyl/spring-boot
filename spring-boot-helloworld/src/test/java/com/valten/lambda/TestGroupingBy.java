package com.valten.lambda;


import com.alibaba.fastjson.JSON;
import com.valten.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * java8 lambda表达式分组
 *
 * @className GroupingBy
 * @package com.valten.lambda
 * @author huangyuanli
 * @date 2020/7/13 15:43
 **/
@SpringBootTest
public class TestGroupingBy {

    private static final List<Student> students = new ArrayList<>();

    static {
        Student s1 = new Student(1L, "肖战", 15, "北京市丰台区");
        Student s2 = new Student(2L, "王一博", 15, "北京市西城区");
        Student s3 = new Student(3L, "杨紫", 17, "北京市东城区");
        Student s4 = new Student(4L, "李现", 17, "北京市丰台区");
        Student s5 = new Student(5L, "肖战", 17, "广东省东莞市");
        Student s6 = new Student(6L, "肖战", 15, "湖北省武汉市");
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.add(s6);
    }

    // 单条件分组
    @Test
    void testGroup() {
        // 按名字分组
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(JSON.toJSONString(collect));
    }

    // 单条件分组计数
    @Test
    void testGroupAndCount() {
        // 按名字分组, 返回每组数量
        Map<String, Long> collect = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        System.out.println(JSON.toJSONString(collect));
    }

    // 单条件分组计算数量
    @Test
    void testGroupAndCompute() {
        // 按名字分组, 返回每组年龄和
        Map<String, Integer> collect = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.summingInt(Student::getAge)));
        System.out.println(JSON.toJSONString(collect));
    }

    // 单条件分组 List -> Set
    @Test
    void testGroupAndListToSet() {
        // 按名字分组, 返回每组年龄和
        Map<String, Set<String>> collect = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.mapping(Student::getAddress, Collectors.toSet())));
        System.out.println(JSON.toJSONString(collect));
    }

    // 多条件分组
    @Test
    void testMultGroup() {
        // 按照姓名和年龄分组
        Map<String, Map<Integer, List<Student>>> groupMap = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy(Student::getAge)));
        System.out.println(JSON.toJSONString(groupMap));
    }
}
