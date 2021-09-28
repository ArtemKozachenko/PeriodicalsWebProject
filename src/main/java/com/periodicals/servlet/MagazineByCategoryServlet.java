package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.Magazine;
import com.periodicals.exception.DBException;
import com.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MagazineByCategoryServlet", urlPatterns = "/magazines/*")
public class MagazineByCategoryServlet extends AbstractServlet {
    private static final long serialVersionUID = 2672247151261079906L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryUrl = request.getPathInfo();
        int index = categoryUrl.indexOf('/', 1);
        if (index > 0) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + categoryUrl.substring(0, index));
            return;
        }

        String[] params = getSortingParams(request);
        String column = "";
        String order = "";
        if (params != null) {
            column = params[0];
            order = params[1];
        }

        int noOfRecords = 0;
        int recordsLimit = getRecordsLimit(request);
        int offset = getOffset();

        List<Magazine> magazines;
        try {
            magazines = getMagazineManager().findMagazinesByCategory(categoryUrl, column,
                    order, recordsLimit, offset);
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (magazines.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/magazines");
            return;
        }

        noOfRecords = getCountOfMagazinesByCategory(categoryUrl);
        setNoOfPages(request, noOfRecords,
                Constants.MAX_PRODUCTS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_MAGAZINE_PAGES);
        setMagazinesInSession(request, magazines);
        request.setAttribute("selectedCategoryUrl", categoryUrl);
        RoutingUtils.forwardToPage("magazineListView.jsp", request, response);

    }
}
