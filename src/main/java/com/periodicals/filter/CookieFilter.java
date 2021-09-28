package com.periodicals.filter;

import com.periodicals.bean.User;
import com.periodicals.exception.DBException;
import com.periodicals.manager.UserManager;
import com.periodicals.util.UserUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CookieFilter")
public class CookieFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User loginedUser = UserUtils.getLoginedUser(session);
        if (loginedUser != null) {
            chain.doFilter(request, response);
            return;
        }

        String userLogin = UserUtils.getUserLoginInCookie(request);
        try {
            User user = UserManager.getInstance().findUserByLogin(userLogin);
            UserUtils.storeLoginedUser(session, user);
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
