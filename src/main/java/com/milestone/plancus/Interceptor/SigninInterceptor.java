package com.milestone.plancus.Interceptor;

import com.milestone.plancus.Api.DTO.MemberDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SigninInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        MemberDTO member = (MemberDTO) httpSession.getAttribute("member");

        if (member == null){
            System.out.println("로그인이 필요합니다.");
            return false;
        }

        httpSession.setMaxInactiveInterval(60 * 60);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
