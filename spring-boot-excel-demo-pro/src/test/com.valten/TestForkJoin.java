import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class TestForkJoin {
    public static void main(String[] args) {
        // 多任务查找空文件夹并新建文件
        ForkJoinPool pool = new ForkJoinPool();
        File file = new File("D://");
        ForkJoinTask task = new EmptyDirTask(file);
        pool.execute(task);
        task.join();
        // 终止线程池
        pool.shutdown();
    }
}

class EmptyDirTask extends RecursiveAction {

    private File file;

    EmptyDirTask(File file) {
        this.file = file;
    }

    @Override
    protected void compute() {
        // 判断文件是否存在
        if (file.exists()) {
            List<EmptyDirTask> taskList = new ArrayList<>();
            File[] files = file.listFiles();
            if (files != null) {
                int length = files.length;
                // 如果文件夹下有文件
                if (length > 0) {
                    for (File f : files) {
                        taskList.add(new EmptyDirTask(f));
                    }
                } else {
                    // 如果文件夹为空
                    System.out.println("空文件路径：" + file.getAbsolutePath());
                }

                // 合并所有任务
                invokeAll(taskList).forEach(EmptyDirTask::join);
            }
        }
    }
}
