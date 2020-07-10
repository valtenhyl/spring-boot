package com.hisign.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @className: EmptyDirAddFileTask
 * @package: com.hisign.controller
 * @describe: 多任务找空文件夹并写入文件
 * @author: huangyuanli
 * @date: 2019/9/5 11:05
 **/
public class EmptyDirAddFileTask extends RecursiveAction {

    /**
     * 要生成的文件名
     */
    private static final String TARGET_FILE_NAME = "/xzqh.txt";
    /**
     * 文件
     */
    private File file;

    /**
     * 文本内容
     */
    private String content;

    public EmptyDirAddFileTask(File file, String content) {
        this.file = file;
        this.content = content;
    }

    @Override
    protected void compute() {
        List<EmptyDirAddFileTask> taskList = new ArrayList<>();
        if (file != null) {
            File[] files = file.listFiles();
            if (files != null) {
                if (files.length > 0) {
                    for (File f : files) {
                        taskList.add(new EmptyDirAddFileTask(f, content));
                    }
                } else if (file.isDirectory()) {
                    //写文件
                    File checkFile = new File(file.getAbsolutePath() + TARGET_FILE_NAME);
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(checkFile);
                        byte[] bytes = content.getBytes("GBK");
                        // 将数组的信息写入文件中
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
                }
            }

            //所有任务合并
            for (EmptyDirAddFileTask t : invokeAll(taskList)) {
                t.join();
            }
        }
    }
}
