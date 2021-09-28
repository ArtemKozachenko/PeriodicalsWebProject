package com.periodicals.manager;

import com.periodicals.bean.Category;
import com.periodicals.exception.DBException;
import com.periodicals.util.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryManager {
    private static CategoryManager instance;
    private final DBManager dbManager;

    public static synchronized CategoryManager getInstance() {
        if (instance == null) {
            instance = new CategoryManager();
        }
        return instance;
    }

    private CategoryManager() {
        dbManager = DBManager.getInstance();
    }

    public List<Category> findAllCategories() throws DBException {
        List<Category> categories;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            categories = dbManager.findAllCategories(connection);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot find all categories", e);
        } finally {
            DBUtils.close(connection);
        }
        return categories;
    }
}
