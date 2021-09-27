package com.periodicals.servlet;

import com.periodicals.bean.Magazine;
import com.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "EditMagazineServlet", urlPatterns = "/cabinet/editMagazine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EditMagazineServlet extends AbstractServlet {
    private static final long serialVersionUID = -3969115559768581120L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String magazineName = request.getParameter("magazineName");
        String description = request.getParameter("description");
        int categoryId1 = Integer.parseInt(request.getParameter("categoryId"));
        int publisherId = Integer.parseInt(request.getParameter("publisherId"));
        String priceStr = request.getParameter("price");
        BigDecimal price = new BigDecimal(priceStr);
        Part filePart = request.getPart("fileImage");

        Magazine magazine = new Magazine(id, magazineName, price,
                description, categoryId1, publisherId);

        if (isImageLoaded(filePart)) {
            String imageLink = uploadImage(filePart);
            magazine.setImageLink(imageLink);
        }

        String message = null;
        String messageType;
        HttpSession session = request.getSession();
        try {
            getMagazineManager().updateMagazine(magazine);
            deleteOldImageIfNewUploaded(request, filePart);
            message = "Magazine with id '" + id + "' has been successfully edited";
            messageType = "success";
        } catch (SQLException exception) {
            exception.printStackTrace();
            messageType = "error";
        }
        updateMagazineInSubscription(session, magazine);
        session.setAttribute("message", message);
        session.setAttribute("messageType", messageType);
        RoutingUtils.sendRedirectToPage(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || !id.matches("\\w+")) {
            response.sendRedirect(request.getContextPath() + "/cabinet?page=1");
            return;
        }
        int magazineId = Integer.parseInt(id);
        Magazine magazine = null;
        try {
            magazine = getMagazineManager().findMagazine(magazineId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (magazine == null) {
            response.sendRedirect(request.getContextPath() + "/cabinet?page=1");
        } else {
            request.setAttribute("magazine", magazine);
            RoutingUtils.forwardToUserCabinetPage("editMagazineForm.jsp", request, response);
        }
    }
}
