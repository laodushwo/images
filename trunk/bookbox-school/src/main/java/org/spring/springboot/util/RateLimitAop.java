package org.spring.springboot.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;
import com.google.common.util.concurrent.RateLimiter;
 
@Component
@Scope
@Aspect
public class RateLimitAop {
	// 每秒只发出指定个令牌(这里用200个)，此处是单进程服务的限流,内部采用令牌捅算法实现
	private static RateLimiter rateLimiter = RateLimiter.create(200.0);

	// Controller层切点  限流
	@Pointcut("@annotation(org.spring.springboot.util.RateLimitAspect)")
	public void requestAspect() {
		
	}
    @Around("requestAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    	Boolean flag = rateLimiter.tryAcquire();
    	Object obj = null;
		if(flag){
			obj = joinPoint.proceed();
//			log.info("成功");
		}else {
//			log.info("哎哟喂，业务太火爆，稍等一会儿呗");
			Result result = new DefaultResult();
			result.setErrors("429", "请求太过频繁，请稍后重试！");
			obj = result;
//			throw new Exception("哎哟喂，业务太火爆，稍等一会儿呗");
		}
    	return obj;
    }
}