package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
//因為是Spring框架是高版本的大於1.8 所以都是默認實現接口 可以不用每個都寫
/**
 * 登錄攔截器
 * @author user
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		//判斷Session中是否有uid, 登錄成功時必然有uid
		if(session.getAttribute("uid") == null){
			//重定向
			response.sendRedirect("/web/login.html");
			//攔截
			return false;
		}
		//放行
		return true;
	}
		
}
