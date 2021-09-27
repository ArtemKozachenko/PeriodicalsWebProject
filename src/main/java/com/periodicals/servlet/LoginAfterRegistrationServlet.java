package com.periodicals.servlet;

import com.periodicals.bean.User;
import com.periodicals.util.RoutingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginAfterRegistrationServlet", urlPatterns = "/login")
public class LoginAfterRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 524123973962958013L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("errorString") != null) {
            doGet(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cabinet");
        }
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
