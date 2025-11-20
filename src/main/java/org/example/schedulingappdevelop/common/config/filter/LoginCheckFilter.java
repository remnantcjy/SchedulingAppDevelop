package org.example.schedulingappdevelop.common.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.schedulingappdevelop.user.dto.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // url 엔드포인트
    private static final String[] whiteList = {"/", "/signup", "/login", "/logout"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("인증 체크 필터 시작 {}, 메서드 {}", requestURI, method);

        // 화이트 리스트 제외 필터
        if (!isLoginCheckPath(requestURI)) {
            log.info("화이트리스트 URL, 인증 체크 제외 {}", request);
            chain.doFilter(request, response);
            return;
        }

        // Get 메서드 제외
        if (method.equals("GET")) {
            log.info("GET 메서드 요청, 인증 체크 제외 {}", requestURI);
            chain.doFilter(request, response);
            return;
        }


        log.info("인증 체크 로직 수행: {} {}", method, requestURI);

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("미인증 사용자 요청 {}", requestURI);
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");
        log.info("loginUser의 Email: {} ", loginUser.getEmail());

        log.info("인증 체크 필터 종료 {}", requestURI);
        chain.doFilter(request, response);
    }

    /**
     * 화이트 리스트의 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
