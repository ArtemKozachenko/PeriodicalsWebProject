package com.periodicals.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class SessionStorageListener implements HttpSessionListener {

    public static  Map<String, HttpSession> getSessionMap(ServletContext servletContext) {
        Map<String, HttpSession> sessionMap = (Map<String, HttpSession>) servletContext.getAttribute("globalSessionMap");
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<>();
            servletContext.setAttribute("globalSessionMap", sessionMap);
        }
        return sessionMap;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Map<String, HttpSession> sessionMap = getSessionMap(session.getServletContext());
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Map<String, HttpSession> sessionMap = getSessionMap(session.getServletContext());
        sessionMap.remove(session.getId());
    }
}
