package com.periodicals.servlet;

import com.periodicals.bean.Magazine;
import com.periodicals.exception.DBException;
import com.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;

@WebServlet(name = "CreateNewMagazineServlet", urlPatterns = "/cabinet/createMagazine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class CreateNewMagazineServlet extends AbstractServlet {
    private static final long serialVersionUID = -4239200880947964824L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String magazineName = request.getParameter("magazineName");
        String description = request.getParameter("description");
        int categoryId1 = Integer.parseInt(request.getParameter("categoryId"));
        int publisherId = Integer.parseInt(request.getParameter("publisherId"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Part filePart = request.getPart("fileImage");
        String imageLink = uploadImage(filePart);

        Magazine magazine = new Magazine(magazineName, price, description,
                imageLink, categoryId1, publisherId);
        String message = null;
        String messageType;
        try {
            getMagazineManager().createNewMagazine(magazine);
            message = "Magazine '" + magazineName + "' has been successfully added";
            messageType = "success";
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("messageType", messageType);
        response.sendRedirect(request.getContextPath() + "/cabinet?page=1");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("message");
        request.setAttribute("url", "createMagazine");
        RoutingUtils.forwardToUserCabinetPage("editMagazineForm.jsp", request, response);
    }
}
