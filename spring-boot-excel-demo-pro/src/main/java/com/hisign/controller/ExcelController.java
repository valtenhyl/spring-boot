package com.hisign.controller;

import com.hisign.task.EmptyDirAddFileTask;
import com.hisign.utils.ExcelUtils;
import com.hisign.utils.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @className: ExcelController
 * @package: com.hisign.controller
 * @describe: excel操作
 * @author: huangyuanli
 * @date: 2019/9/5 9:44
 **/
@RestController
public class ExcelController {

    /**
     * 根路径
     */
    private static final String ROOT_PATH = "D://test/";

    /**
     * 文件存放位置
     */
    private static final String DATA_FILE_PATH = "static/data.xlsx";

    /**
     * 文件名
     */
    private static final String SOURCE_FILE_NAME = "data.xlsx";

    @GetMapping("excel")
    public String handler() {
        try {
            long startTime = System.currentTimeMillis();
            //excel模板路径
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DATA_FILE_PATH);

            Map<String, Object> objectMap = ExcelUtils.readExcel(inputStream, SOURCE_FILE_NAME);
            Object pathObj = objectMap.get("0");
            // 优雅的把Object转为List<String>
            List<String> paths = new ArrayList<>();
            if (pathObj instanceof ArrayList<?>) {
                for (Object o : (List<?>) pathObj) {
                    paths.add((String) o);
                }
            }
            // 创建所有路径
            FileUtils.createDirs(paths, ROOT_PATH);


            Object contentObj = objectMap.get("1");
            // 优雅的把Object转为List<String>
            List<String> contents = new ArrayList<>();
            if (contentObj instanceof ArrayList<?>) {
                for (Object o : (List<?>) contentObj) {
                    contents.add((String) o);
                }
            }
            // List<String>排序
            contents.sort(String::compareTo);

            // 拼接
            StringBuilder sb = new StringBuilder();
            contents.forEach(content -> sb.append(content).append("\n"));
            String contentStr = sb.toString();

            // 多任务查找空文件夹并新建文件
            ForkJoinPool pool = new ForkJoinPool();
            File file = new File(ROOT_PATH);
            ForkJoinTask task = new EmptyDirAddFileTask(file, contentStr);
            pool.execute(task);
            task.join();
            // 终止线程池
            pool.shutdown();

            long endTime = System.currentTimeMillis();
            return "SUCCESS，耗时：" + (endTime - startTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "发生异常，" + e.getMessage();
        }
    }
}