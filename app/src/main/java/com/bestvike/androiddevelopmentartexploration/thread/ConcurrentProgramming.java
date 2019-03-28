package com.bestvike.androiddevelopmentartexploration.thread;

import com.example.beaselibrary.network.okHttp.utils.Exceptions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 并发编程的实用案例
 */
public class ConcurrentProgramming {

    static int i = 0;

    private synchronized static void println(String str){
        i++;
        System.out.println(str);
    }

    public static void main(String[] args) {

//        ExtendThread extendThread = new ExtendThread();
//        extendThread.start();
//
//        RunnableThread runnableThread = new RunnableThread();
//        Thread thread = new Thread(runnableThread);
//        thread.start();

//        CallableThread callableThread = new CallableThread();
//        FutureTask<String> stringFutureTask = new FutureTask<>(callableThread);
//        Thread thread = new Thread(stringFutureTask);
//        thread.start();
//
//        try {
//            println(stringFutureTask.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        //-------------------------------以上是多线程的基本实用----------------------------

        ExecutorService exceptions = null;

        //Single Thread Executor：只有一个线程的线程池，顺序执行
       /* exceptions = Executors.newSingleThreadExecutor();
        //提交实现到线程池
        exceptions.submit(new Runnable() {
            @Override
            public void run() {
                println("线程池newSingleThreadExecutor");
            }
        });*/

        //Cached Thread Pool：缓存线程池，超过60s池内线程没有被使用，则删掉。就是一个动态的线程池，我们不需要关心线程数
       /* exceptions = Executors.newCachedThreadPool();
        //提交实现到线程池
        exceptions.submit(new Runnable() {
            @Override
            public void run() {
                println("线程池newCachedThreadPool");
            }
        });*/

        //Fixed Thread Pool：固定数量的线程池
        //参数为线程数
//        exceptions = Executors.newFixedThreadPool(3);
//        //提交实现到线程池
//        exceptions.submit(new Runnable() {
//            @Override
//            public void run() {
//                println("线程池newFixedThreadPool:");
//            }
//        });

//        Scheduled Thread Pool：用于调度指定时间执行任务的线程池
        //参数为线程数
        /*ScheduledExecutorService pool = Executors.newScheduledThreadPool(8);

        *//*
         * 提交到线程池
         * 参数1：Runnable
         * 参数2：初始延迟时间
         * 参数3：间隔时间
         * 参数4：时间单位
         *//*
        pool.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        println("线程池newScheduledThreadPool"+i);
                    }
                },
                1000,
                1000,
                TimeUnit.MILLISECONDS//毫秒
        );*/

        //Single Thread Scheduled Pool：调度指定时间执行任务的线程池，只有一个线程
//        final ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
//        pool.scheduleAtFixedRate(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        println("线程池newScheduledThreadPool"+i);
//                        if(i > 10)
//                            pool.shutdown();
//                    }
//                },
//                1000,
//                1000,
//                TimeUnit.MILLISECONDS//毫秒
//        );

        /*pool.schedule(new Runnable() {
            @Override
            public void run() {
                println("线程池newSingleThreadScheduledExecutor:"+i);
            }
        }, 1000, TimeUnit.MILLISECONDS);*/

    }


    /**
     * 线程的实现方式1
     * 继承Thread
     */
    static class ExtendThread extends Thread{
        @Override
        public void run() {
            println("Thread");

        }
    }

    /**
     * 实现Runnable接口
     */
    static class RunnableThread implements Runnable{
        @Override
        public void run() {
            println("Runnable");
        }
    }

    /**
     * Callable和Future
     */
    static class CallableThread implements Callable<String>{

        @Override
        public String call() throws Exception {
            println("CallableThread");
            return "CallableThread1";
        }
    }
}
