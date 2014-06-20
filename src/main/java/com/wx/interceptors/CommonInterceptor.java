package com.wx.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor extends HandlerInterceptorAdapter {
    final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
    private long preTime = 0l;
    private long postTime= 0l;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("控制层接口调用之前");
        logger.info("contextPATH:{},servletPath:{},URI:{},URL:{},sessionID{}",new String[]{request.getContextPath(),request.getServletPath(),request.getRequestURI(),request.getRequestURL().toString(),request.getSession().getId()});
        String controllerType=handler.getClass().getName();
        logger.info("该请求调用的是处理器类型:"+controllerType);
        String requestPath=new UrlPathHelper().getLookupPathForRequest(request);
        logger.info("该请求调用的路径:{}",requestPath);
        if(handler.getClass().equals(HandlerMethod.class)){
            String invokeControllerClassName=((HandlerMethod)handler).getBeanType().toString();
            String invokeControllerMethodName=((HandlerMethod)handler).getMethod().getName();
            String invokeControllerFullMethodSign=handler.toString();
            logger.info("控制器名称:{},方法名称:{},完整的方法签名:{}.",new String[]{invokeControllerClassName,invokeControllerMethodName,invokeControllerFullMethodSign});
        }else{
        }
		String str = request.getRequestURI();
		if (str != null && str.contains(request.getContextPath() + "/resources")) {
		    logger.info("用户请求匹配静态资源，系统判定为正常！");
		    return true;
		}
		//TODO 此处添加不需要登录判断的业务路径
        preTime = System.currentTimeMillis();
        request.setAttribute("preTime", preTime);
        return true;
    }
    

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("控制层接口调用之后");
        postTime = System.currentTimeMillis();
        long temp = postTime - preTime;
        logger.info("控制层接口调用耗时{}毫秒", temp);
        request.setAttribute("postTime", postTime);
        /*System.out.println(handler.getClass().getName()+""+handler.getClass().getSimpleName());
        System.out.println("a="+((HandlerMethod)handler).getBean());
        System.out.println("b="+((HandlerMethod)handler).getMethod().getName());
        System.out.println("c="+((HandlerMethod)handler).toString());
        System.out.println("d="+((HandlerMethod)handler).getMethodAnnotation(org.springframework.web.bind.annotation.RequestMapping.class));
        System.out.println("e="+((HandlerMethod)handler).getBeanType());
        System.out.println("f="+((HandlerMethod)handler).getReturnType().toString());
        System.out.println("g="+((HandlerMethod)handler).getReturnType().getClass());
        MethodParameter[] ff=((HandlerMethod)handler).getMethodParameters();
        if(ff!=null&&ff.length>0){
            for (MethodParameter aFf : ff) {
                System.out.println("ffffff=" + aFf.getParameterName());
            }
        }*/
        /*RequestMappingHandlerMapping rmhm=new RequestMappingHandlerMapping();
        String path=rmhm.getUrlPathHelper().getLookupPathForRequest(request);
        HandlerExecutionChain hec=rmhm.getHandler(request);
        String handlerToString=hec.getHandler().toString();*/
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //由于这个方法是在渲染视图后运行，因此在该方法中设置request属性没有效果
        logger.info("视图渲染之后");
        long completionTime = System.currentTimeMillis();
        long temp = completionTime - postTime;
        logger.info("视图渲染耗时{}毫秒", temp);
        super.afterCompletion(request, response, handler, ex);
    }

}