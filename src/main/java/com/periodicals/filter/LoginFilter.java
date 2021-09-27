package com.periodicals.filter;

import com.periodicals.bean.User;
import com.periodicals.util.PasswordUtils;
import com.periodicals.util.SubscriptionUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if ("loginAttempt=Y".equals(request.getQueryString())) {
            chain.doFilter(req, resp);
            return;
        }
        if (!"Y".equals(request.getParameter("loginAttempt"))) {
            chain.doFilter(req, resp);
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean rememberMe = "Y".equals(rememberMeStr);

        User user = null;
        boolean hasError = false;
        String errorString = null;
        if (login == null || password == null || login.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            try {
                user = PasswordUtils.verifyThePlainTextPassword(password, login);
                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
            }
        }

        if (hasError) {
            user = new User();
            user.setLogin(login);
            user.setPassword(password);
            request.setAttribute("user", user);
            request.setAttribute("errorString", errorString);
            chain.doFilter(request, response);
            return;
        }

        try {
            SubscriptionUtils.updateSubscriptionsStatus(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        HttpSession session = request.getSession();
        UserUtils.storeLoginedUser(session, user);
        if (rememberMe) {
            UserUtils.storeUserCookie(response, user);
        } else {
            UserUtils.deleteUserCookie(response);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
