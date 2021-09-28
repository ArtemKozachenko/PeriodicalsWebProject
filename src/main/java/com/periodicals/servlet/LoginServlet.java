package com.periodicals.servlet;

import com.periodicals.bean.User;
import com.periodicals.exception.DBException;
import com.periodicals.util.PasswordUtils;
import com.periodicals.util.RoutingUtils;
import com.periodicals.util.SubscriptionUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginAfterRegistrationServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 524123973962958013L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url;
        if ("Y".equals(request.getParameter("loginAttempt"))) {
            url = "/magazines";
        } else {
            url = "/cabinet";
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
            } catch (DBException exception) {
                exception.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        HttpSession session = request.getSession();
        if (hasError) {
            user = new User();
            user.setLogin(login);
            user.setPassword(password);
            session.setAttribute("user", user);
            session.setAttribute("errorString", errorString);
            response.sendRedirect(request.getContextPath() + url);
            return;
        }

        try {
            SubscriptionUtils.updateSubscriptionsStatus(user);
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        UserUtils.storeLoginedUser(session, user);
        if (rememberMe) {
            UserUtils.storeUserCookie(response, user);
        } else {
            UserUtils.deleteUserCookie(response);
        }
        response.sendRedirect(request.getContextPath() + url);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginedUser = UserUtils.getLoginedUser(request.getSession());
        if (loginedUser != null) {
            response.sendRedirect(request.getContextPath() + "/magazines");
            return;
        }
        request.setAttribute("registrationPage", "Y");
        request.setAttribute("loginPage", "Y");
        RoutingUtils.forwardToPage("login-form.jsp", request, response);
    }
}
