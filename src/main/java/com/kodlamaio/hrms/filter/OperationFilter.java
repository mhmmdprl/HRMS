package com.kodlamaio.hrms.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kodlamaio.hrms.enums.JwtConfig;
import com.kodlamaio.hrms.util.TokenProvider;

@Component
public class OperationFilter implements HandlerInterceptor {

	@Autowired
	private TokenProvider tokenProvider;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();
		String header = request.getHeader(JwtConfig.HEADER_STRING.getValue());
	

		if (header != null && header.startsWith(JwtConfig.TOKEN_PREFIX.getValue()) && !"/error".equals(url)) {

			String token = header.replace(JwtConfig.TOKEN_PREFIX.getValue() + " ", "");

			List<String> userOperations = tokenProvider.getAuthoritiesFromToken(token);
              
			String operationCode=url+ '_'+request.getMethod();
			 boolean isAuthenticate=userOperations.contains(operationCode);
			if(!isAuthenticate) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return false;
			}
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
