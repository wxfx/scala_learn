package com.next.interview.thread;

import javax.sound.midi.Soundbank;

public class ThreadCreate {
    public static void main(String[] args) {
        //Thread1 thread1 = new Thread1();
        //Thread2 thread2 = new Thread2();
        //启动线程
        //thread1.start();
        //thread2.start();
        Thread thread3 = new Thread(new Thread3());
        Thread thread4 = new Thread(new Thread4());
        //启动线程
        thread3.start();
        thread4.start();

        /**
         * 使用run方法时，所有的代码都运行在main线程中
         * thread3/thread4的run()方法只是一个普通方法而已，不是启动一个新的线程
         * 使用start方法时，线程的代码是运行在各自的线程中
         * start方法会创建一个新的子线程并启动
         */
        thread3.run();
        thread4.run();
    }
}

/*
    方式1：通过继承Thread类
 */
class Thread1 extends Thread{


    @Override
    public void run() {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}

class Thread2 extends Thread{

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}

/*
    线程创建方式2：实现Runable接口
 */
class Thread3 implements Runnable{
    @Override
    public void run() {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}

/**
 * 两种实现方式的异同点：
 * 共同点：将希望之星的代码放到run方法，然后调用start方法来启动线程
 * 区别：
 * 继承Thread：类是单一继承原则
 * 实现Runable接口：可以实现多个接口，推荐使用
 */
class Thread4 implements Runnable{
    @Override
    public void run() {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
