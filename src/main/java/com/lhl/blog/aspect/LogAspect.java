package com.lhl.blog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Pointcut("execution(* com.lhl.blog.controller.*.*(..))")
    public void log(){

    }
    @Before("log()")
    public void doBefore(JoinPoint jp){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = jp.getSignature().getDeclaringTypeName()+"."+ jp.getSignature().getName();
        Object[] args = jp.getArgs();
        LogList result = new LogList(url,ip,classMethod,args);

        logger.info("Request info : {}",result);

    }
    @After("log()")
    public void doAfter(){



    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void returnResult(Object result){

        logger.info("======result :  {}=======",result);

    }


    private class LogList {

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public LogList(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "LogList{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
