package com.valten.lambda;

import com.valten.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * anyMatch/allMatch/noneMatch（匹配）
 * <p>
 * anyMatch：Stream 中任意一个元素符合传入的 predicate，返回 true
 * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
 * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
 *
 * @className Match
 * @package com.valten.lambda
 * @author huangyuanli
 * @date 2020/7/13 16:38
 **/
public class TestMatch {

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

    @Test
    void testAnyMatch() {
        boolean flag = students.stream().anyMatch(s -> "北京市丰台区".equals(s.getAddress()));
        if (flag) {
            System.out.println("有北京市丰台区人");
        }
    }

    @Test
    void testAllMatch() {
        boolean flag = students.stream().allMatch(s -> s.getAge() >= 15);
        if (flag) {
            System.out.println("所有学生都满15周岁");
        }
    }

    @Test
    void testNoneMatch() {
        boolean flag = students.stream().noneMatch(s -> "杨洋".equals(s.getName()));
        if (flag) {
            System.out.println("没有叫杨洋的同学");
        }
    }
}
