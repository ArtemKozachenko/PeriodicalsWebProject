package com.periodicals.util;

import com.periodicals.constant.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SortingUtils {
    private SortingUtils() {
    }

    public static String getSortingFromRequest(HttpServletRequest request) {
        String sortParam = request.getParameter("sort");
        HttpSession session = request.getSession();
        String sortingName = setSortingName(sortParam);
        if (sortingName != null) {
            setSortingInSession(session, sortParam);
            request.setAttribute("sortingName", sortingName);
        } else {
            sortParam = getSortingFromSession(session);
            if (sortParam != null) {
                request.setAttribute("sortingName", setSortingName(sortParam));
            }
        }
        return sortParam;
    }

    public static String getSortingFromSession(HttpSession session) {
        return (String) session.getAttribute(Constants.ATT_NAME_SORTING_NAME);
    }

    public static void setSortingInSession(HttpSession session, String sortParam) {
        session.setAttribute(Constants.ATT_NAME_SORTING_NAME, sortParam);
    }

    private static String setSortingName(String sortParam) {
        if (sortParam == null) return null;
        Constants.SortingParamName paramName = Constants.SortingParamName.fromValue(sortParam);
        switch (paramName) {
            case NAME_ASC:
                return "Name: A-Z";
            case NAME_DESC:
                return "Name: Z-A";
            case PRICE_ASC:
                return "Price: Low to High";
            case PRICE_DESC:
                return "Price: High to Low";
            default:
                return null;
        }
    }


}
