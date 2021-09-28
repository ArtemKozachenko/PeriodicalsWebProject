package com.periodicals.servlet;

import com.periodicals.util.SortingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = -9192433072307381182L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sorting = SortingUtils.getSortingFromSession(session);
        session.invalidate();

        HttpSession newSession = request.getSession();
        SortingUtils.setSortingInSession(newSession, sorting);
        UserUtils.deleteUserCookie(response);
        response.sendRedirect(request.getContextPath() + "/magazines");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/magazines");
    }
}
