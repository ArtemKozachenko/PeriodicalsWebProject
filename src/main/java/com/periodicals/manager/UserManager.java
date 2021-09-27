package com.periodicals.manager;

import com.periodicals.bean.Subscription;
import com.periodicals.bean.User;
import com.periodicals.util.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private final DBManager dbManager;

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {
        dbManager = DBManager.getInstance();
    }

    public User findUserByLogin(String login) throws SQLException {
        User user = null;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            user = dbManager.findUserByLogin(connection, login);
        } finally {
            DBUtils.close(connection);
        }
        return user;
    }

    public User findUserByEmail(String email) throws SQLException {
        User user = null;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            user = dbManager.findUserByEmail(connection, email);
        } finally {
            DBUtils.close(connection);
        }
        return user;
    }

    public void insertNewUser(User user) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.createNewUser(connection, user);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void updateWalletBalance(User user) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateWalletBalance(connection, user);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void createNewSubscription(Subscription subscription) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.createNewSubscription(connection, subscription);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void updateSubscriptionsStatus(List<Subscription> subscriptions) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateSubscriptionsStatus(connection, subscriptions);
        } finally {
            DBUtils.close(connection);
        }
    }

    public List<User> findAllUsers(int limit, int offset) throws SQLException {
        List<User> users;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            users = dbManager.findAllUsers(connection, limit, offset);
        } finally {
            DBUtils.close(connection);
        }
        return users;
    }

    public int getCountOfAllUsers() throws SQLException {
        Connection connection = null;
        int count = 0;
        try {
            connection = DBUtils.getInstance().getConnection();
            count = dbManager.getCountOfAllUsers(connection);
        } finally {
            DBUtils.close(connection);
        }
        return count;
    }

    public void updateUserStatus(int userId, String status) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateUserStatus(connection, userId, status);
        } finally {
            DBUtils.close(connection);
        }
    }
}
