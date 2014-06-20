package com.wx.interceptors;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

import java.util.concurrent.Callable;

/**
 * Created by Shawn on 2014/6/20.
 */
public class TimeoutCallableProcessingInterceptor extends CallableProcessingInterceptorAdapter {
    @Override
    public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
        throw new IllegalStateException("[" + task.getClass().getName() + "] timed out");
    }
}
