package com.periodicals.listener;

import com.periodicals.constant.Constants;
import com.periodicals.manager.CategoryManager;
import com.periodicals.manager.PublisherManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class PeriodicalsApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CategoryManager categoryManager = CategoryManager.getInstance();
        PublisherManager publisherManager = PublisherManager.getInstance();
        ServletContext servletContext = sce.getServletContext();
        try {
            servletContext.setAttribute(Constants.CATEGORY_LIST, categoryManager.findAllCategories());
            servletContext.setAttribute(Constants.PUBLISHER_LIST, publisherManager.findAllPublishers());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
