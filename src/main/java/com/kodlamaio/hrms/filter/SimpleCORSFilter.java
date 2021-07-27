package com.kodlamaio.hrms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {
		logger.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");

		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void destroy() {

	}

//	  @Override
//	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//	        HttpServletRequest request = (HttpServletRequest) req;
//	        HttpServletResponse response = (HttpServletResponse) res;
//	        String clientOrigin = request.getHeader("origin");
//	        response.addHeader("Access-Control-Allow-Origin", clientOrigin);
//	        response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
//	        response.setHeader("Access-Control-Allow-Credentials", "true");
//	        response.setHeader("Access-Control-Max-Age", "3600");
//	        response.setHeader("Access-Control-Allow-Headers",
//	                "Origin, Accept, X-Requested-With, Content-Type, " +
//	                        "Access-Control-Request-Method, Access-Control-Request-Headers");
//
//	        if (request.getMethod().equals("OPTIONS")) {
//	            response.setStatus(HttpServletResponse.SC_OK);
//	        } else {
//	            chain.doFilter(request, response);
//	        }
//	    }
//
//	    @Override
//	    public void init(FilterConfig filterConfig) {
//	    }
//
//	    @Override
//	    public void destroy() {
//	    }

}
