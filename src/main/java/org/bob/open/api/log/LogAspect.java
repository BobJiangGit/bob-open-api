package org.bob.open.api.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bob.open.api.dto.response.ResultResponse;
import org.bob.open.api.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * <p>统一日志记录</p>
 *
 * Created by Bob Jiang on 2016/12/17.
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final String URI_ERROR = "/error";

    /**
     * 定义日志切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAspect() {}

    @Around("logAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LogInfo logInfo = new LogInfo(point, request);

        logInfo.beforeLog();

        try {
            Object result = point.proceed(args);
            logInfo.afterLog(result);
            return result;
        } catch (SystemException e) {
            logInfo.throwLog(e.getCode() + " : " + e.getMessage(), e);
            return ResultResponse.define(Integer.valueOf(e.getCode()), e.getMessage());
        } catch (Throwable e) {
            logInfo.throwLog(e.getMessage(), e);
            return ResultResponse.failed(e.getMessage());
        }
    }

    /**
     * 日志信息
     */
    public class LogInfo {
        private Class clazz;
        private String methodName;
        private String uri;
        private Object[] params;
        private String method;
        private Object result;
        private long beginTime;
        private long costMilliseconds;

        public LogInfo(JoinPoint point, HttpServletRequest request) {
            this.clazz  = point.getTarget().getClass();
            this.methodName = point.getSignature().getName();
            this.uri        = request.getRequestURI();
            this.method     = request.getMethod();
            this.params     = point.getArgs();
            this.beginTime  = System.currentTimeMillis();
        }

        public String getClazzName() {
            return clazz.getSimpleName();
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getParams() {
            return Arrays.toString(params);
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getCostMilliseconds() {
            return costMilliseconds;
        }

        public void setCostMilliseconds(long costMilliseconds) {
            this.costMilliseconds = costMilliseconds;
        }

        public void beforeLog() {
            if (URI_ERROR.equals(uri)) {
                return;
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Request  ][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 参数:").append(getParams());
            logger.info(buffer.toString());
        }

        public void afterLog(Object result) {
            if (URI_ERROR.equals(uri)) {
                return;
            }
            setResult(result);
            setCostMilliseconds(System.currentTimeMillis() - getBeginTime());
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Response ][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 响应结果：").append(result).append(" 耗时：").append(costMilliseconds).append("ms");
            logger.info(buffer.toString());
        }

        public void throwLog(String message, Throwable e) {
            setCostMilliseconds(System.currentTimeMillis() - getBeginTime());
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Exception][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 参数:").append(getParams());
            buffer.append(" 耗时：").append(costMilliseconds).append("ms");
            buffer.append(" 异常信息：").append(message);
            logger.error(buffer.toString(), e);
        }
    }

}
