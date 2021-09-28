package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.Magazine;
import com.periodicals.bean.User;
import com.periodicals.exception.DBException;
import com.periodicals.util.RoutingUtils;
import com.periodicals.util.SubscriptionUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserAccountServlet", urlPatterns = "/cabinet")
public class UserAccountServlet extends AbstractServlet {
    private static final long serialVersionUID = -523116498185851422L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String money = request.getParameter("money");
        HttpSession session = request.getSession();
        String message = null;
        String messageType = null;
        if (money != null && !money.isEmpty()) {
            BigDecimal value = new BigDecimal(money);
            User user = UserUtils.getLoginedUser(session);
            BigDecimal balance = user.getWallet().getAmountOfMoney();
            user.getWallet().setAmountOfMoney(balance.add(value));
            try {
                getUserManager().updateWalletBalance(user);
                message = "Your wallet has been successfully replenished for $ " + value;
                messageType = "success";
            } catch (DBException exception) {
                exception.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        session.setAttribute("message", message);
        session.setAttribute("messageType", messageType);
        response.sendRedirect(request.getContextPath() + "/cabinet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserUtils.getLoginedUser(request.getSession());
        try {
            SubscriptionUtils.updateSubscriptionsStatus(user);
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        int noOfSubscriptionRecords = user.getSubscriptions().size();
        int begin = setBeginValue(request);
        setEndValue(request, begin);
        setNoOfPages(request, noOfSubscriptionRecords,
                Constants.MAX_SUBSCRIPTIONS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_SUB_PAGES);

        int noOfMagazineRecords = 0;
        int recordsLimit = getRecordsLimit(request);
        int offset = getOffset();
        List<Magazine> magazines = new ArrayList<>();
        try {
            magazines = getMagazineManager().findAllMagazines(null, null,
                    recordsLimit, offset);
            noOfMagazineRecords = getMagazineManager().getCountOfAllMagazines();
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        setNoOfPages(request, noOfMagazineRecords,
                Constants.MAX_PRODUCTS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_MAGAZINE_PAGES);
        setMagazinesInSession(request, magazines);

        int noOfUserRecords = 0;
        int recordsUsrLimit = getUsrRecordsLimit(request);
        int offsetUsr = getUsrOffset();
        List<User> users = new ArrayList<>();
        try {
            users = getUserManager().findAllUsers(recordsUsrLimit, offsetUsr);
            noOfUserRecords = getUserManager().getCountOfAllUsers();
        } catch (DBException exception) {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        setNoOfPages(request, noOfUserRecords,
                Constants.MAX_USERS_PER_HTML_PAGE, Constants.ATT_NAME_NO_OF_USER_PAGES);
        request.setAttribute("usersList", users);

        RoutingUtils.forwardToUserCabinetPage("user-cabinet-tabs.jsp", request, response);
    }
}
