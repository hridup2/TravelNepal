//package com.travelNepal.config;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import java.io.IOException;
//
//@Slf4j
//public class CorsFilter implements Filter {
//
//    private HandlerExceptionResolver handlerExceptionResolver;
//
//    public CorsFilter(HandlerExceptionResolver handlerExceptionResolver) {
//        this.handlerExceptionResolver = handlerExceptionResolver;
//    }
//
//    public CorsFilter() {
//        log.info("CorsFilter Init");
//    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//
//        try {
//            HttpServletRequest request = (HttpServletRequest) req;
//            log.info("Request From the Frontend {}", request);
//
//            // Log the request URL
//            log.info("Request URL: {}", request.getRequestURL());
//
//
//            HttpServletResponse response = (HttpServletResponse) res;
//
//            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//            response.setHeader("Access-Control-Expose-Headers", "X-Custom-Header");
//
//            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//                response.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                chain.doFilter(req, res);
//            }
//            log.info("CorsFilter execution completed");
//        } catch (Exception ex) {
//            handlerExceptionResolver.resolveException((HttpServletRequest) req, (HttpServletResponse) res, null, ex);
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//    }
//
//    @Override
//    public void destroy() {
//    }
//}