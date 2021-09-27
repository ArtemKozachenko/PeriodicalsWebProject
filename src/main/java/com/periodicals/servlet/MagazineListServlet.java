package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.Magazine;
import com.periodicals.manager.DBException;
import com.periodicals.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MagazineListServlet", urlPatterns = "/magazines")
public class MagazineListServlet extends AbstractServlet {
    private static final long serialVersionUID = -5675556951348362153L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        List<Magazine> magazines = new ArrayList<>();
        try {
            magazines = getMagazineManager().findAllMagazines(column, order,
                    recordsLimit, offset);
            noOfRecords = getMagazineManager().getCountOfAllMagazines();
        } catch (SQLException | DBException exception) {
            exception.printStackTrace();
        }

        /*} catch (DBException exception) {
            exception.printStackTrace();
        }*/

        setNoOfPages(request, noOfRecords,
                Constants.MAX_PRODUCTS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_MAGAZINE_PAGES);
        setMagazinesInSession(request, magazines);
        RoutingUtils.forwardToPage("magazineListView.jsp", request, response);
    }
}
