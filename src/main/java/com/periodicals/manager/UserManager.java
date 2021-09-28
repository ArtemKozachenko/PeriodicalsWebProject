package com.periodicals.manager;

import com.periodicals.bean.Subscription;
import com.periodicals.bean.User;
import com.periodicals.exception.DBException;
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

    public User findUserByLogin(String login) throws DBException {
        User user;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            user = dbManager.findUserByLogin(connection, login);
        } catch (SQLException e) {
            //1 log error ...
            DBUtils.rollback(connection);
            throw new DBException("Cannot find user with login: " + login, e);
        } finally {
            DBUtils.close(connection);
        }
        return user;
    }

    public User findUserByEmail(String email) throws DBException {
        User user;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            user = dbManager.findUserByEmail(connection, email);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot find user with email: " + email, e);
        } finally {
            DBUtils.close(connection);
        }
        return user;
    }

    public void insertNewUser(User user) throws DBException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.createNewUser(connection, user);
        } catch (SQLException e) {
            //1 log error ...
            DBUtils.rollback(connection);
            throw new DBException("Cannot insert new user into DB", e);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void updateWalletBalance(User user) throws DBException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateWalletBalance(connection, user);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot update wallet balance of user: " + user.getLogin(), e);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void createNewSubscription(Subscription subscription) throws DBException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.createNewSubscription(connection, subscription);
        } catch (SQLException e) {
            //1 log error ...
            DBUtils.rollback(connection);
            throw new DBException("Cannot create new subscription: ", e);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void updateSubscriptionsStatus(List<Subscription> subscriptions) throws DBException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateSubscriptionsStatus(connection, subscriptions);
        } catch (SQLException e) {
            //1 log error ...
            DBUtils.rollback(connection);
            throw new DBException("Cannot update subscriptions status", e);
        } finally {
            DBUtils.close(connection);
        }
    }

    public List<User> findAllUsers(int limit, int offset) throws DBException {
        List<User> users;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            users = dbManager.findAllUsers(connection, limit, offset);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot find all users", e);
        } finally {
            DBUtils.close(connection);
        }
        return users;
    }

    public int getCountOfAllUsers() throws DBException {
        Connection connection = null;
        int count = 0;
        try {
            connection = DBUtils.getInstance().getConnection();
            count = dbManager.getCountOfAllUsers(connection);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot get count of users", e);
        } finally {
            DBUtils.close(connection);
        }
        return count;
    }

    public void updateUserStatus(int userId, String status) throws DBException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateUserStatus(connection, userId, status);
        } catch (SQLException e) {
            //1 log error ...
            throw new DBException("Cannot update user status with id: " + userId, e);
        } finally {
            DBUtils.close(connection);
        }
    }
}
