package com.periodicals.util;

import com.periodicals.bean.Subscription;
import com.periodicals.bean.User;
import com.periodicals.manager.UserManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionUtils {

    private SubscriptionUtils() {
    }

    public static void updateSubscriptionsStatus(User user) throws SQLException {
        List<Subscription> subscriptions = user.getSubscriptions();
        List<Subscription> expiredSubscriptions = new ArrayList<>();
        for (Subscription sub : subscriptions) {
            if (sub.getStatus().equals("active") && LocalDate.now().isAfter(sub.getEndDate())) {
                sub.setStatus("expired");
                expiredSubscriptions.add(sub);
            }
        }
        if (!expiredSubscriptions.isEmpty()) {
            UserManager.getInstance().updateSubscriptionsStatus(expiredSubscriptions);
        }
    }

    public static void frozeUserSubscriptions(User user) throws SQLException {
        List<Subscription> subscriptions = user.getSubscriptions();
        for (Subscription sub : subscriptions) {
            sub.setStatus("frozen");
        }
        if (!subscriptions.isEmpty()) {
            UserManager.getInstance().updateSubscriptionsStatus(subscriptions);
        }
    }

    public static void restoreUserSubscriptionsStatus(User user) throws SQLException {
        List<Subscription> subscriptions = user.getSubscriptions();
        for (Subscription sub : subscriptions) {
            if (!LocalDate.now().isAfter(sub.getEndDate())) {
                sub.setStatus("active");
            }
        }
        if (!subscriptions.isEmpty()) {
            UserManager.getInstance().updateSubscriptionsStatus(subscriptions);
        }
    }
}
