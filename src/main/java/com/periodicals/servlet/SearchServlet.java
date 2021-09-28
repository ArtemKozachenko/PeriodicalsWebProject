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

@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchServlet extends AbstractServlet {
    private static final long serialVersionUID = -4685771063924959501L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("query");
        if (searchQuery == null && request.getQueryString() == null) {
            response.sendRedirect(request.getContextPath() + "/magazines");
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
            magazines = getMagazineManager().findMagazinesBySearchQuery(searchQuery, column, order,
                    recordsLimit, offset);
            noOfRecords = magazines.size();
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        setNoOfPages(request, noOfRecords,
                Constants.MAX_PRODUCTS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_MAGAZINE_PAGES);
        request.setAttribute("magazineList", magazines);
        request.setAttribute("searchQuery", "&query=" + searchQuery);
        RoutingUtils.forwardToPage("magazineListView.jsp", request, response);
    }
}
