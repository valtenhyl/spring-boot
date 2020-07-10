package com.hisign.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: FileUtils
 * @package: com.hisign.utils
 * @describe: 文件操作工作累
 * @author: huangyuanli
 * @date: 2019/9/5 9:43
 **/
@Component
public class FileUtils {

    /**
     * @describe: 创建文件夹
     * @param: paths
     * @param: rootPath
     * @return: void
     * @author: huangyuanli
     * @date: 2019/9/5 9:43
     **/
    public static void createDirs(List<String> paths, String rootPath) {
        Map<Integer, Object> map = new HashMap<>();
        // 文件根路径
        File rootFile = new File(rootPath);
        // 如果不存在，创建目录
        if (!rootFile.exists()) {
            boolean mkdir = rootFile.mkdir();
            System.out.println(mkdir);
        }
        // 根路径存入集合
        map.put(0, rootFile.getAbsolutePath());
        int index;
        for (String path : paths) {
            // 按"/"切割
            String[] split = path.split("/");
            // 层级
            index = split.length - 1;
            // 获取上一级路径
             String filename = map.get(index - 1).toString();
            // 拼接新的路径
            filename = filename + File.separator + split[index];
            File file = new File(filename);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                System.out.println(mkdir);
            }
            map.put(index, filename);
        }
    }
}
