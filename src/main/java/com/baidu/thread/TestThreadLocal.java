package com.baidu.thread;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2015/4/13
 * Time: 20:35
 */
public class TestThreadLocal {



    public static void main(String[] args) {

        ThreadLocal<String> a = new ThreadLocal<String>();
        a.set("a");
        ThreadLocal<String> b = new ThreadLocal<String>();
        b.set("b");
        System.out.println(a.get() + b.get());
    }
}
