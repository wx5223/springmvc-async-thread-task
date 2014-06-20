package com.wx.controller;


import com.wx.async.TestTask;
import com.wx.async.TestThread;
import com.wx.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.*;

/**
 * Created by Shawn on 2014/6/19.
 */
@Controller
@RequestMapping("/async")
public class AsyncController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "this is a test return :我是中国人";
    }

    @RequestMapping("/test1")
    @ResponseBody
    public Test test1() {
        return new Test("中国人");
    }
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @RequestMapping("/callable")
    @ResponseBody
    public Callable<String> callable() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "Callable result";
            }
        };
    }

    @RequestMapping("/over")
    @ResponseBody
    public Callable<String> over() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(20000);
                return "Callable result";
            }
        };
    }
    @RequestMapping("/task")
    @ResponseBody
    public String task() {
        FutureTask<String> futureTask = new FutureTask<String>(new TestTask("task1"));
        taskExecutor.execute(futureTask);
        return "task";
    }

    @RequestMapping("/taskWait")
    @ResponseBody
    public String taskWait() {
        FutureTask<String> futureTask = new FutureTask<String>(new TestTask("task1"));
        taskExecutor.execute(futureTask);
        try {
            futureTask.get(4000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            futureTask.cancel(true);
        }
        return "task";
    }

    @RequestMapping("/thread")
    @ResponseBody
    public String thread() {
        taskExecutor.execute(new TestThread("thread1"));
        return "thread";
    }
}
