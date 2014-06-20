package com.wx.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 直接调用线程->Thread/Runnable
 * Created by Shawn on 2014/6/20.
 */
public class TestThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(TestThread.class);
    private String name;

    public TestThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        log.debug("start task name:{}", name);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("end task name:{}", name);
    }
}
