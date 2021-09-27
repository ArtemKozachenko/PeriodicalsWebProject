package com.periodicals.filter;

import com.periodicals.util.SortingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LogoutFilter")
public class LogoutFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if ("signOut=Y".equals(request.getQueryString())) {
            chain.doFilter(req, resp);
            return;
        }
        if (!"Y".equals(request.getParameter("signOut"))) {
            chain.doFilter(req, resp);
            return;
        }
        HttpSession session = request.getSession();
        String sorting = SortingUtils.getSortingFromSession(session);
        session.invalidate();

        HttpSession newSession = request.getSession();
        SortingUtils.setSortingInSession(newSession, sorting);
        UserUtils.deleteUserCookie(response);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
