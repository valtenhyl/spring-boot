package com.valten.lambda;

import com.valten.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 集合排序
 *
 * @author huangyuanli
 * @className Sort
 * @package com.valten.lambda
 * @date 2020/7/13 16:53
 **/
public class TestSort {

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

    // 集合排序（指定排序规则）
    @Test
    void testSorted() {
        students.stream()
                .sorted((stu1, stu2) -> {
                    if (stu1.getAge() >= 0 && stu2.getAge() >= 0) {
                        return Long.compare(stu1.getAge(), stu2.getAge());
                    } else if (stu1.getAge() < 0 && stu2.getAge() < 0) {
                        return Long.compare(stu2.getAge(), stu1.getAge());
                    } else {
                        return Long.compare(stu2.getAge(), stu1.getAge());
                    }
                })
                .forEach(System.out::println);
    }

    // 集合排序（指定排序规则）
    @Test
    void testSorted2() {
        students.stream()
                // 按年龄升序排序，相同年龄按id降序排序
                .sorted((stu1, stu2) -> Long.compare(stu2.getId(), stu1.getId()))
                .sorted(Comparator.comparingInt(Student::getAge))
                .forEach(System.out::println);
    }
}
