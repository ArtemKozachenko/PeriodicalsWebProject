package com.periodicals.servlet;

import com.periodicals.exception.DBException;
import com.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DeleteMagazineServlet", urlPatterns = "/cabinet/deleteMagazine")
public class DeleteMagazineServlet extends AbstractServlet {
    private static final long serialVersionUID = 6739703273293447704L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || !id.matches("\\w+")) {
            response.sendRedirect(request.getContextPath() + "/cabinet?page=1");
            return;
        }
        int magazineId = Integer.parseInt(id);
        String message = null;
        String messageType;
        try {
            getMagazineManager().deleteMagazine(magazineId);
            deleteOldImage(request);
            message = "Magazine with id '" + magazineId + "' has been successfully deleted";
            messageType = "success";
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("messageType", messageType);
        RoutingUtils.sendRedirectToPage(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
