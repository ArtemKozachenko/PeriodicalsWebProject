package com.periodicals.servlet;

import com.periodicals.exception.DBException;
import com.periodicals.util.RoutingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChangeUserStatusServlet", urlPatterns = "/cabinet/changeUserStatus")
public class ChangeUserStatusServlet extends AbstractServlet {
    private static final long serialVersionUID = 6294941774069223977L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String login = request.getParameter("login");
        String statusValue = request.getParameter("statusValue");
        if (id == null || !id.matches("\\w+")) {
            RoutingUtils.sendRedirectToUserListTab(request, response);
            return;
        }
        int userId = Integer.parseInt(id);
        String message = null;
        String messageType;
        String status;
        if (statusValue.equals("block")) {
            status = "banned";
        } else {
            status = "active";
        }

        try {
            getUserManager().updateUserStatus(userId, status);
            UserUtils.changeUserStatus(getServletContext(), status, userId);
            message = "Status of user '" + login + "' has been set to " + "'" + status + "'";
            messageType = "success";
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("messageType", messageType);
        RoutingUtils.sendRedirectToUserListTab(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
