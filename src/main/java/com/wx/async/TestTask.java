package com.wx.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * 可以返回结果->Task
 * Created by Shawn on 2014/6/20.
 */
public class TestTask implements Callable<String> {
    private static final Logger log = LoggerFactory.getLogger(TestTask.class);
    private String name;

    public TestTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        log.debug("start task name:{}", name);
        Thread.sleep(20000);
        log.debug("end task name:{}", name);
        return null;
    }
}
