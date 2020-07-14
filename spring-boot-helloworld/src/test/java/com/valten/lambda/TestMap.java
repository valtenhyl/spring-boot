package com.valten.lambda;

import com.alibaba.fastjson.JSON;
import com.valten.model.Student;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集合转换
 *
 * @author huangyuanli
 * @className ToMap
 * @package com.valten.lambda
 * @date 2020/7/13 17:07
 **/
public class TestMap {
    private static final List<Student> students = new ArrayList<>();
    private static final java.util.Map<String, String> onepiece = new HashMap<>();

    static {
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "袁天罡", 17, "浙江");
        Student s5 = new Student(4L, "李淳风", 17, "浙江");
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);

        onepiece.put("路飞", "找到传说中的One Piece，成为海贼王");
        onepiece.put("索隆", "打败鹰眼成为世界第一的剑豪，帮助路飞成为海贼王");
        onepiece.put("三治", "All Blue、透明果实（后放弃）");
        onepiece.put("布鲁克", "回到双子岬跟鲸鱼拉布重逢");
    }

    // 将Map值转为值的长度
    @Test
    void testToMap() {
        java.util.Map<String, Integer> dream = onepiece.entrySet().stream().collect(Collectors.toMap(java.util.Map.Entry::getKey, e -> e.getValue().length()));
        System.out.println(JSON.toJSONString(dream));
    }

    // 将Map值转为值的长度
    @Test
    void testToMap2() {
        java.util.Map<String, Integer> dream = onepiece.entrySet().stream().collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue().length()), HashMap::putAll);
        System.out.println(JSON.toJSONString(dream));
    }

    // 对象转map，相同值留旧值或者新值
    @Test
    void testToMap3() {
        java.util.Map<Long, String> map = students.stream().collect(Collectors.toMap(Student::getId, Student::getName, (oldVal, newVal) -> oldVal));
        System.out.println(JSON.toJSONString(map));

        java.util.Map<Long, String> map2 = students.stream().collect(Collectors.toMap(Student::getId, Student::getName, (oldVal, newVal) -> newVal));
        System.out.println(JSON.toJSONString(map2));
    }

    // 对象转map
    @Test
    void testToMap4() {
        // 在地址前面加上部分信息，只获取地址输出
        List<String> addresses = students.stream().map(s -> "住址:" + s.getAddress()).collect(Collectors.toList());
        List<String> addresses1 = students.stream().map(Student::getAddress).collect(Collectors.toList());
        String addresses2 = students.stream().map(Student::getAddress).collect(Collectors.joining(","));
        addresses.forEach(System.out::println);
        addresses1.forEach(System.out::println);
        System.out.println(addresses2);
        String addresses3 = StringUtils.join(addresses1.toArray(), ",");
        System.out.println(addresses3);
    }

    // 对Map值求和
    @Test
    void testToMap5() {
        java.util.Map<String, Integer> map = new HashMap<>();
        map.put("aa", 1);
        map.put("bb", 2);
        map.put("bc", 3);
        map.put("dd", 5);
        Integer sum = map.entrySet().stream().filter(entry -> entry.getKey().startsWith("b")).mapToInt(java.util.Map.Entry::getValue).sum();
        System.out.println(sum);

        Integer sum1 = map.values().stream().filter(integer -> integer > 1).mapToInt(i -> i).sum();
        System.out.println(sum1);
    }
}
