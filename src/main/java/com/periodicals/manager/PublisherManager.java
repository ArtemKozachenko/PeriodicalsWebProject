package com.periodicals.manager;

import com.periodicals.bean.Publisher;
import com.periodicals.util.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PublisherManager {
    private static PublisherManager instance;
    private final DBManager dbManager;

    public static synchronized PublisherManager getInstance() {
        if (instance == null) {
            instance = new PublisherManager();
        }
        return instance;
    }

    private PublisherManager() {
        dbManager = DBManager.getInstance();
    }

    public List<Publisher> findAllPublishers() throws SQLException {
        List<Publisher> publishers;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            publishers = dbManager.findAllPublishers(connection);
        } finally {
            DBUtils.close(connection);
        }
        return publishers;
    }
}
