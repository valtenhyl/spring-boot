本文由 简悦 SimpRead 转码， 原文地址 www.cnblogs.com

Fork/Join 框架详解

Fork/Join 框架是 Java 7 提供的一个用于并行执行任务的框架，是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。Fork/Join 框架要完成两件事情：

- 任务分割：首先 Fork/Join 框架需要把大的任务分割成足够小的子任务，如果子任务比较大的话还要对子任务进行继续分割
- 执行任务并合并结果：分割的子任务分别放到双端队列里，然后几个启动线程分别从双端队列里获取任务执行。子任务执行完的结果都放在另外一个队列里，启动一个线程从队列里取数据，然后合并这些数据

ForkJoinTask

使用 Fork/Join 框架，首先需要创建一个 ForkJoin 任务。该类提供了在任务中执行 fork 和 join 的机制。通常情况下我们不需要直接集成 ForkJoinTask 类，只需要继承它的子类，Fork/Join 框架提供了两个子类:

- RecursiveAction  
用于没有返回结果的任务
- RecursiveTask  
用于有返回结果的任务

ForkJoinPool

ForkJoinTask 需要通过 ForkJoinPool 来执行。

任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务 (工作窃取算法)；

Fork/Join 框架的实现原理

ForkJoinPool 由 ForkJoinTask 数组和 ForkJoinWorkerThread 数组组成，ForkJoinTask 数组负责将存放程序提交给 ForkJoinPool，而 ForkJoinWorkerThread 负责执行这些任务;

ForkJoinTask 的 fork 方法的实现原理

当我们调用 ForkJoinTask 的 fork 方法时，程序会把任务放在 ForkJoinWorkerThread 的 pushTask 的 workQueue 中，异步地执行这个任务，然后立即返回结果，代码如下：

    public final ForkJoinTask<V> fork() {
        Thread t;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            ((ForkJoinWorkerThread)t).workQueue.push(this);
        else
            ForkJoinPool.common.externalPush(this);
        return this;
    }
    
    

pushTask 方法把当前任务存放在 ForkJoinTask 数组队列里。然后再调用 ForkJoinPool 的 signalWork() 方法唤醒或创建一个工作线程来执行任务。代码如下：

    final void push(ForkJoinTask<?> task) {
        ForkJoinTask<?>[] a; ForkJoinPool p;
        int b = base, s = top, n;
        if ((a = array) != null) {    // ignore if queue removed
            int m = a.length - 1;     // fenced write for task visibility
            U.putOrderedObject(a, ((m & s) << ASHIFT) + ABASE, task);
            U.putOrderedInt(this, QTOP, s + 1);
            if ((n = s - b) <= 1) {
                if ((p = pool) != null)
                    p.signalWork(p.workQueues, this);
            }
            else if (n >= m)
                growArray();
        }
    }
    
    

ForkJoinTask 的 join 方法的实现原理

Join 方法的主要作用是阻塞当前线程并等待获取结果。让我们一起看看 ForkJoinTask 的 join 方法的实现，代码如下：

    public final V join() {
    	int s;
        if ((s = doJoin() & DONE_MASK) != NORMAL){
        	reportException(s);
        }
        return getRawResult();
    }
    
    

它首先调用 doJoin 方法，通过 doJoin() 方法得到当前任务的状态来判断返回什么结果，任务状态有 4 种：已完成（NORMAL）、被取消（CANCELLED）、信号（SIGNAL）和出现异常（EXCEPTIONAL）；  
如果任务状态是已完成，则直接返回任务结果；  
如果任务状态是被取消，则直接抛出 CancellationException；  
如果任务状态是抛出异常，则直接抛出对应的异常；  
doJoin 方法的实现，代码如下：

    private int doJoin() {
    	int s;
    	Thread t;
    	ForkJoinWorkerThread wt;
    	ForkJoinPool.WorkQueue w;
        return (s = status) < 0 ? s :
                ((t = Thread.currentThread()) instanceof 								ForkJoinWorkerThread) ? (w = (wt = 										(ForkJoinWorkerThread)t).workQueue).tryUnpush(this) && (s = 				doExec()) < 0 ? s : wt.pool.awaitJoin(w, this, 0L) : 				externalAwaitDone();
    }
    
    

doExec() :

    final int doExec() {
    	int s; 
    	boolean completed;
    	if ((s = status) >= 0) {
    		try {
    			completed = exec();
    		} catch (Throwable rex) {
    			return setExceptionalCompletion(rex);
    		}
    		if (completed){
    			s = setCompletion(NORMAL);
    		}
    	}
    	return s;
    }
    
    

在 doJoin() 方法里，首先通过查看任务的状态，看任务是否已经执行完成，如果执行完成，则直接返回任务状态；如果没有执行完，则从任务数组里取出任务并执行。如果任务顺利执行完成，则设置任务状态为 NORMAL，如果出现异常，则记录异常，并将任务状态设置为 EXCEPTIONAL

Fork/Join 框架的异常处理

ForkJoinTask 在执行的时候可能会抛出异常，但是我们没办法在主线程里直接捕获异常，所以 ForkJoinTask 提供了 isCompletedAbnormally() 方法来检查任务是否已经抛出异常或已经被取消了，并且可以通过 ForkJoinTask 的 getException 方法获取异常。代码如下：

    if(task.isCompletedAbnormally())
    {
        System.out.println(task.getException());
    }
    
    

getException 方法返回 Throwable 对象，如果任务被取消了则返回 CancellationException。如果任务没有完成或者没有抛出异常则返回 null:

    public final Throwable getException() {
    	int s = status & DONE_MASK;
    	return ((s >= NORMAL) ? null :
            (s == CANCELLED) ? new CancellationException() :
            getThrowableException());
    }
    
    

DEMO

需求：求 1+2+3+4 的结果  
分析：Fork/Join 框架首先要考虑到的是如何分割任务，如果希望每个子任务最多执行两个数的相加，那么我们设置分割的阈值是 2，由于是 4 个数字相加，所以 Fork/Join 框架会把这个任务 fork 成两个子任务，子任务一负责计算 1+2，子任务二负责计算 3+4，然后再 join 两个子任务的结果。因为是有结果的任务，所以必须继承 RecursiveTask，实现代码如下：

    import java.util.concurrent.ExecutionException;
    import java.util.concurrent.ForkJoinPool;
    import java.util.concurrent.Future;
    import java.util.concurrent.RecursiveTask;
    
    /**
     *
     * @author aikq
     * @date 2018年11月21日 20:37
     */
    public class ForkJoinTaskDemo {
    
    	public static void main(String[] args) {
    		ForkJoinPool pool = new ForkJoinPool();
    		CountTask task = new CountTask(1,4);
    		Future<Integer> result = pool.submit(task);
    		try {
    			System.out.println("计算结果=" + result.get());
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		} catch (ExecutionException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    class CountTask extends RecursiveTask<Integer>{
    	private static final long serialVersionUID = -7524245439872879478L;
    
    	private static final int THREAD_HOLD = 2;
    
    	private int start;
    	private int end;
    
    	public CountTask(int start,int end){
    		this.start = start;
    		this.end = end;
    	}
    
    	@Override
    	protected Integer compute() {
    		int sum = 0;
    		//如果任务足够小就计算
    		boolean canCompute = (end - start) <= THREAD_HOLD;
    		if(canCompute){
    			for(int i=start;i<=end;i++){
    				sum += i;
    			}
    		}else{
    			int middle = (start + end) / 2;
    			CountTask left = new CountTask(start,middle);
    			CountTask right = new CountTask(middle+1,end);
    			//执行子任务
    			left.fork();
    			right.fork();
    			//获取子任务结果
    			int lResult = left.join();
    			int rResult = right.join();
    			sum = lResult + rResult;
    		}
    		return sum;
    	}
    }
    
    

通过这个例子，我们进一步了解 ForkJoinTask，ForkJoinTask 与一般任务的主要区别在于它需要实现 compute 方法，在这个方法里，首先需要判断任务是否足够小，如果足够小就直接执行任务。如果不足够小，就必须分割成两个子任务，每个子任务在调用 fork 方法时，又会进入 compute 方法，看看当前子任务是否需要继续分割成子任务，如果不需要继续分割，则执行当前子任务并返回结果。使用 join 方法会等待子任务执行完并得到其结果

本文由博客一文多发平台 OpenWrite 发布！
