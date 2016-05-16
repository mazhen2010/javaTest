package com.baidu.thread;

/**
 * 测试方法无限循环和线程无限循环对CPU的占用.
 * User: mazhen01
 * Date: 2016/5/13
 * Time: 17:42
 */
public class TestLoopCPU {

    private Long expireTime = 100 * 1000L;

    public void doLoop() {

        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();
        while(true) {
            endTime = System.currentTimeMillis();
            if ((endTime - startTime) > expireTime) {
                System.out.print("doLoop end.");
                break;
            }
        }

    }

    public void doThreadLoop() {
        Runnable runnable = new Runnable() {
            public void run() {
                Long startTime = System.currentTimeMillis();
                Long endTime = System.currentTimeMillis();
                while(true) {

                    try {
                        Thread.sleep(1L);
                        System.out.println("I am live!");
                    } catch (InterruptedException e) {
                        System.out.println("doThreadLoop interrupt.");
                        e.printStackTrace();
                    }

                    endTime = System.currentTimeMillis();
                    if ((endTime - startTime) > expireTime) {
                        break;
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        try {
            thread.join();
            thread.start();
            Thread.sleep(100L);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("doThreadLoop end.");
    }

    public static void main(String[] args) {
        TestLoopCPU cpu = new TestLoopCPU();
//        cpu.doLoop();
        cpu.doThreadLoop();
    }
}
