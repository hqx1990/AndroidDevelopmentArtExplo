package com.bestvike.androiddevelopmentartexploration.thread;

public class Synchronizeddemo {

    private static int count = 0;
    private static int intThread = 100;


    public static void main(String[] args) {
        Thread  thread1 = null;
        for (int i = 0 ; i < intThread;i++){
             thread1 = new Thread(new ChildTask());
             thread1.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    private synchronized static void count(){
        for (int i = 0;i<10;i++){
            count++;
        }
    }


  static   class ChildTask implements Runnable{
        @Override
        public void run() {
            count();
        }
    }
}
