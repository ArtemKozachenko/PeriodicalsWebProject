package com.periodicals.util;

import com.periodicals.constant.Constants;
import com.periodicals.bean.User;
import com.periodicals.listener.SessionStorageListener;
import com.periodicals.exception.DBException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

public class UserUtils {

    private UserUtils() {
    }

    public static void changeUserStatus(ServletContext servletContext, String status, int userId) throws DBException {
        Collection<HttpSession> sessions = SessionStorageListener.getSessionMap(servletContext).values();
        User user;
        for (HttpSession session : sessions) {
            user = UserUtils.getLoginedUser(session);
            if (user != null && user.getId().equals(userId)) {
                user.setStatus(status);
                if (status.equals("active")) {
                    SubscriptionUtils.restoreUserSubscriptionsStatus(user);
                } else {
                    SubscriptionUtils.frozeUserSubscriptions(user);
                }
                return;
            }
        }
    }

    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }

    public static User getLoginedUser(HttpSession session) {
        return (User) session.getAttribute("loginedUser");
    }

    public static void storeUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(Constants.ATT_NAME_USER_NAME, user.getLogin());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getUserLoginInCookie(HttpServletRequest request) {
        String userName = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.ATT_NAME_USER_NAME)) {
                    userName = cookie.getValue();
                }
            }
        }
        return userName;
    }

    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(Constants.ATT_NAME_USER_NAME, "");
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}
