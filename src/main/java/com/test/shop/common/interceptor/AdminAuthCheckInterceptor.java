package com.test.shop.common.interceptor;

import com.test.shop.admin.service.AdminService;
import com.test.shop.common.annotation.AdminAuthCheck;
import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAuthCheckInterceptor implements HandlerInterceptor {

	private final AdminService adminService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod handlerMethod) {
			AdminAuthCheck adminAuthCheck = handlerMethod.getMethodAnnotation(AdminAuthCheck.class);
			if (adminAuthCheck != null) {
				HttpSession session = request.getSession();
				String sessionId = request.getHeader("X-Auth-Token");
				if (sessionId == null) {
					throw new GlobalException(ErrorCode.NOT_LOGIN);
				}
				String adminId = (String) session.getAttribute("adminId");

				if (session.isNew() || !sessionId.equals(session.getId()) || adminId == null || !adminService.existsMember(adminId)) {
					throw new GlobalException(ErrorCode.NOT_LOGIN);
				}
			}
		}
		return true;
	}
}
