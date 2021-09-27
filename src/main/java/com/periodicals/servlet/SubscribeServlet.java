package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.Magazine;
import com.periodicals.bean.Subscription;
import com.periodicals.bean.User;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "SubscribeServlet", urlPatterns = "/subscribe")
public class SubscribeServlet extends AbstractServlet {
    private static final long serialVersionUID = 5635303081154225077L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String idRegex = "\\w+";
        if (id == null || !id.matches(idRegex)) {
            doGet(request, response);
            return;
        }
        int magazineId = Integer.parseInt(id);
        HttpSession session = request.getSession();
        Magazine magazine = findMagazineById(session, magazineId);
        BigDecimal magazinePrice = magazine.getPrice();
        User user = UserUtils.getLoginedUser(session);
        if (!canAfford(user, magazinePrice)) {
            doGet(request, response);
            return;
        }

        LocalDate localDate = LocalDate.now();
        Subscription subscription = new Subscription(localDate, localDate.plusMonths(1), user, magazine);
        try {
            getUserManager().createNewSubscription(subscription);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        user.getSubscriptions().add(subscription);
        session.setAttribute("subscribe", "Y");
        response.sendRedirect(request.getContextPath() + "/cabinet" + "?pageSub=" +
                (int) Math.ceil(user.getSubscriptions().size() * 1.0 / Constants.MAX_SUBSCRIPTIONS_PER_HTML_PAGE));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/magazines");
    }
}
