package com.valten.controller;

import com.valten.utils.ExcelUtil;
import com.valten.utils.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RestController
public class ExcelController {

    @GetMapping("excel")
    public String handler() {
        try {
            long startTime = System.currentTimeMillis();
            //excel模板路径
            //File srcFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/data.xlsx");//打包后无法获取文件
//            ClassPathResource classPathResource = new ClassPathResource("static/data.xlsx");
//            InputStream inputStream = classPathResource.getInputStream();
//            File srcFile = File.createTempFile("test", ".txt");
//            try {
//                FileUtils.copyInputStreamToFile(inputStream, srcFile);
//            } finally {
//                IOUtils.closeQuietly(inputStream);
//            }

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/data.xlsx");

            Map<String, Object> objectMap = ExcelUtil.readExcel(inputStream, "data.xlsx");
            Object pathObj = objectMap.get("0");
            // 优雅的把Object转为List<String>
            List<String> paths = new ArrayList<>();
            if (pathObj instanceof ArrayList<?>) {
                for (Object o : (List<?>) pathObj) {
                    paths.add((String) o);
                }
            }
            // 获取所有路径
            List<String> dirs = FileUtil.createDirs(paths, "C://test/");


            Object contentObj = objectMap.get("1");
            // 优雅的把Object转为List<String>
            List<String> contents = new ArrayList<>();
            if (contentObj instanceof ArrayList<?>) {
                for (Object o : (List<?>) contentObj) {
                    contents.add((String) o);
                }
            }
            // List<String>排序
            contents.sort((o1, o2) -> {
                if (o1.compareToIgnoreCase(o2) < 0) {
                    return -1;
                } else if (o1.compareToIgnoreCase(o2) > 0) {
                    return 1;
                }
                return 0;
            });

            // 拼接
            StringBuilder sb = new StringBuilder();
            contents.forEach(content -> {
                sb.append(content).append("\n");
            });
            String contentStr = sb.toString();

            // 获取下面没有子文件夹的路径
            List<String> lastDirs = new ArrayList<>();
            dirs.forEach(dir -> {
                File file = new File(dir);
                if (Objects.requireNonNull(file.listFiles()).length == 0) {
                    lastDirs.add(dir);
                }
            });


            lastDirs.forEach(dir -> {
                File checkFile = new File(dir + "/xzqh.txt");
                FileOutputStream out = null;
                try {
                    // 1、检查目标文件是否存在，不存在则创建
                    if (!checkFile.exists()) {
                        // 创建目标文件
                        checkFile.createNewFile();
                    }
                    out = new FileOutputStream(checkFile);
                    byte[] bytes = contentStr.getBytes("GBK");
                    //将数组的信息写入文件中
                    out.write(bytes);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 释放资源
                    if (null != out) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            long endTime = System.currentTimeMillis();
            return "SUCCESS，耗时：" + (endTime - startTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "发生异常，" + e.getMessage();
        }
    }
}
