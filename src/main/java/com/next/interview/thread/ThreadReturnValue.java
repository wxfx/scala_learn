package com.next.interview.thread;

import java.util.concurrent.*;

/**
 * 需求：主线程中获取子线程的返回值
 */
public class ThreadReturnValue {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //threadWait();
        //threadJoin();
        //threadFutureTask();
        threadPool();
    }

    //1)主线程等待
    public static void threadWait() throws InterruptedException {
        LearnThread learnThread = new LearnThread();
        Thread thread = new Thread(learnThread);
        thread.start();

        while(learnThread.learnDate == null){
            System.out.println("莫要急！");
            Thread.sleep(100);
        }
        System.out.println("返回日期：" + learnThread.learnDate);
    }

    //2.使用线程的join来获取子线程返回值
    public static void threadJoin() throws InterruptedException {
        LearnThread learnThread = new LearnThread();
        Thread thread = new Thread(learnThread);
        thread.start();

        //等待线程消亡
        thread.join();
        System.out.println("返回日期：" + learnThread.learnDate);
    }

    //3、Callable接口：FutureTask
    public static void threadFutureTask()  throws ExecutionException, InterruptedException {
        LearnCallable learnCallable = new LearnCallable();
        FutureTask<String> stringFutureTask = new FutureTask<String>(learnCallable);
        new Thread(stringFutureTask).start();//启动线程

        if (!stringFutureTask.isDone()){
            System.out.println("稍等片刻哈！");
        }
        System.out.println("季节是：" + stringFutureTask.get());
    }
    //4、Callable接口：线程池
    public static void threadPool()  throws ExecutionException, InterruptedException {
        LearnCallable learnCallable = new LearnCallable();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future= executorService.submit(learnCallable);

        if (!future.isDone()){
            System.out.println("稍等片刻哈！");
        }
        try {
            System.out.println("季节是：" + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }


}

class LearnThread implements Runnable{
    public String learnDate;

    @Override
    public void run() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        learnDate = "20200926";
    }
}

 class LearnCallable implements Callable<String>{

    @Override
    public String call() throws Exception {
       String learnSeason = "秋天";
        System.out.println("准备开始执行复杂的业务逻辑...");
        Thread.sleep(1000);
        System.out.println("逻辑执行完毕！");
        return learnSeason;
    }
}
