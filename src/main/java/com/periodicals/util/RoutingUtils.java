package com.periodicals.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoutingUtils {
    private RoutingUtils() {
    }

    public static void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentPage", "page/" + jspPage);
        req.getRequestDispatcher("/WEB-INF/views/page-template.jsp").forward(req, resp);
    }

    public static void forwardToUserCabinetPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentPage", jspPage);
        req.getRequestDispatcher("/WEB-INF/views/user-cabinet-template.jsp").forward(req, resp);
    }

    public static void sendRedirectToPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageStr = request.getParameter("page");
        if (pageStr == null || !pageStr.matches("\\w+")) {
            response.sendRedirect(request.getContextPath() + "/cabinet?page=1");
            return;
        }
        int page = Integer.parseInt(pageStr);
        response.sendRedirect(request.getContextPath() + "/cabinet?page=" + page);
    }

    public static void sendRedirectToUserListTab(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageStr = request.getParameter("pageUsr");
        if (pageStr == null || !pageStr.matches("\\w+")) {
            response.sendRedirect(request.getContextPath() + "/cabinet?pageUsr=1");
            return;
        }
        int page = Integer.parseInt(pageStr);
        response.sendRedirect(request.getContextPath() + "/cabinet?pageUsr=" + page);
    }

}
