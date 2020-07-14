package com.valten.collection;

import com.valten.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 *
 *
 * @className List
 * @package com.valten.collection
 * @author huangyuanli
 * @date 2020/7/13 17:02
 **/
public class List {

    private static final java.util.List<Student> students = new ArrayList<>();
    private static final java.util.List<Student> students2 = new ArrayList<>();

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

        students2.add(s3);
        students2.add(s5);
        students2.add(s6);
    }

    /**
     * 集合取交集（引用对象）
     *
     * @author huangyuanli
     * @date 2020/7/13 17:04
     */
    @Test
    void testDistinct3() {
        //引用对象的去重，引用对象要实现hashCode和equal方法，否则去重无效
        students.retainAll(students2);
        students.forEach(System.out::println);
    }
}
