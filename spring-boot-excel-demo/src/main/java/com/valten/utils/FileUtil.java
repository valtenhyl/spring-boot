package com.valten.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: FileUtils
 * @package: com.hisign.utils
 * @describe: 文件操作工作累
 * @author: huangyuanli
 * @date: 2019/9/4 22:18
 **/
@Component
public class FileUtil {

    /**
     * @describe: 创建文件夹
     * @param: paths
     * @param: rootPath
     * @return: Map<Integer, Object>
     * @author: huangyuanli
     * @date: 2019/9/4 22:20
     **/
    public static List<String> createDirs(List<String> paths, String rootPath) {
        List<String> dirs = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        // 文件根路径
        File rootFile = new File(rootPath);
        rootFile.mkdir();
        map.put(0, rootFile.getAbsolutePath());
        int index = 0;
        for (int i = 0; i < paths.size(); i++) {
            String[] split = paths.get(i).split("/");
            index = split.length - 1;
            String filename;
            filename = map.get(index - 1).toString();
            filename = filename + File.separator + split[index];
            File file = new File(filename);
            if (!file.exists()) {
                file.mkdir();
            }
            map.put(index, filename);
            dirs.add(filename);
        }
        return dirs;
    }
}
