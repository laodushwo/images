package org.spring.springboot.util.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.spring.springboot.util.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.UsersVO;

@Configuration
public class AuthorHandlerInterceptor implements HandlerInterceptor {

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	response.setHeader("Access-Control-Allow-Methods", "GET, POST");
    	response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
    	response.setHeader("Content-Type", "text/html; charset=utf-8");
    	 // 验证权限
        if (this.hasPermission(request,response, handler)) {
            return true;
        }
        //  null == request.getHeader("x-requested-with") TODO 暂时用这个来判断是否为ajax请求
        // 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
        String requestPath = request.getServletPath();
        StringBuffer stringBuffer = _doNotLogin(request, requestPath);
        response.sendRedirect(stringBuffer.toString());
        return false;
    }
    
    private StringBuffer _doNotLogin(HttpServletRequest request, String requestPath) throws UnsupportedEncodingException {
		String queryString = request.getQueryString();
		StringBuffer buffer = new StringBuffer(request.getContextPath());

		buffer.append("/wx/index");
		buffer.append("?u").append("=");
		buffer.append(requestPath);
		if (StringUtils.isNotBlank(queryString)) {
			buffer.append("?").append(URLEncoder.encode(queryString, "UTF8"));
		}
		return buffer;
	}
    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            // 获取方法上的注解
//            AuthorAnnotation authorAnnotation = handlerMethod.getMethod().getAnnotation(AuthorAnnotation.class);
//            // 如果方法上的注解为空 则获取类的注解
//            if (authorAnnotation == null) {
//            	authorAnnotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthorAnnotation.class);
//            }
//            // 如果标记了注解，则判断权限
//            if (authorAnnotation != null && StringUtils.isNotBlank(authorAnnotation.value())) {
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
//                Set<String> permissionSet = adminUserService.getPermissionSet();
//                if (CollectionUtils.isEmpty(permissionSet) ){
//                    return false;
//                }
//                return permissionSet.contains(requiredPermission.value());
            	UsersVO usersVO = WebUtils.getUsers(request);
            	if(usersVO == null) {
//            		return false;
            	}
//            }
//        }
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	response.setHeader("Access-Control-Allow-Methods", "GET, POST");
    	response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
    	response.setHeader("Content-Type", "text/html; charset=utf-8");
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
