package com.periodicals.filter;

import com.periodicals.bean.User;
import com.periodicals.constant.type.Role;
import com.periodicals.util.UserUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User loginedUser = UserUtils.getLoginedUser(session);
        if (loginedUser.getRole().equals(Role.ADMIN) && loginedUser.getStatus().equals("active")) {
            chain.doFilter(req, resp);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/cabinet");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
