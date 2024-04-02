package com.test.shop.common.interceptor;

import com.test.shop.common.annotation.UserAuthCheck;
import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import com.test.shop.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberAuthCheckInterceptor implements HandlerInterceptor {

	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod handlerMethod) {
			UserAuthCheck userAuthCheck = handlerMethod.getMethodAnnotation(UserAuthCheck.class);
			if (userAuthCheck != null) {
				HttpSession session = request.getSession();
				String sessionId = request.getHeader("X-Auth-Token");
				if (sessionId == null) {
					throw new GlobalException(ErrorCode.NOT_LOGIN);
				}
				Long userNo = (Long) session.getAttribute("userNo");

				if (session.isNew() || !sessionId.equals(session.getId()) || userNo == null || !memberService.existsMember(userNo)) {
					throw new GlobalException(ErrorCode.NOT_LOGIN);
				}
			}
		}
		return true;
	}
}
