package com.periodicals.manager;

import com.periodicals.bean.*;
import com.periodicals.constant.type.Role;
import com.periodicals.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static DBManager instance;

    private static final String SELECT_ALL_MAGAZINES_WITH_LIMIT_QUERY = "SELECT m.*, c.category_name, p.publisher_name\n" +
            "FROM magazines m\n" +
            "JOIN categories c\n" +
            "ON m.category_id = c.id\n" +
            "JOIN publishers p\n" +
            "ON m.publisher_id = p.id\n" +
            "ORDER BY m.%s %s\n" +
            "LIMIT %d, %d";
    private static final String SELECT_ALL_MAGAZINES_QUERY = "SELECT m.*, c.category_name, p.publisher_name\n" +
            "FROM magazines m\n" +
            "JOIN categories c\n" +
            "ON m.category_id = c.id\n" +
            "JOIN publishers p\n" +
            "ON m.publisher_id = p.id\n" +
            "ORDER BY m.id desc";
    private static final String SELECT_ALL_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String SELECT_ALL_PUBLISHERS_QUERY = "SELECT * FROM publishers";
    private static final String COUNT_ALL_MAGAZINES_QUERY = "SELECT COUNT(*) FROM magazines";
    private static final String COUNT_ALL_USERS_QUERY = "SELECT COUNT(*) FROM users";
    private static final String SELECT_MAGAZINES_BY_CATEGORY_LINK_QUERY = "SELECT m.*, c.category_name, p.publisher_name\n" +
            "FROM magazines m\n" +
            "JOIN categories c\n" +
            "ON m.category_id = c.id\n" +
            "JOIN publishers p\n" +
            "ON m.publisher_id = p.id\n" +
            "WHERE c.url = ?" +
            "ORDER BY m.%s %s\n" +
            "LIMIT %d, %d";
    private static final String FIND_USER_BY_SPECIFIC_COLUMN_QUERY = "SELECT u.*, r.role_name, w.balance\n" +
            "FROM users u\n" +
            "JOIN roles r\n" +
            "ON u.role_id = r.id\n" +
            "JOIN e_wallet w\n" +
            "ON u.id = w.user_id\n" +
            "WHERE u.%s = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.*, r.role_name\n" +
            "FROM users u\n" +
            "JOIN roles r\n" +
            "ON u.role_id = r.id\n" +
            "LIMIT %d, %d";
    private static final String INSERT_NEW_USER_QUERY = "INSERT INTO users " +
            "(login, password, salt, email, first_name, last_name, gender, creation_date) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SEARCH_MAGAZINES_BY_QUERY = "SELECT m.*, c.category_name, p.publisher_name\n" +
            "FROM magazines m\n" +
            "JOIN categories c\n" +
            "ON m.category_id = c.id\n" +
            "JOIN publishers p\n" +
            "ON m.publisher_id = p.id\n" +
            "WHERE m.magazine_name LIKE ? OR m.description LIKE ?" +
            "ORDER BY m.%s %s\n" +
            "LIMIT %d, %d";
    private static final String CREATE_USER_E_WALLET = "INSERT INTO e_wallet (user_id) VALUES (?)";
    private static final String UPDATE_E_WALLET_BALANCE = "UPDATE e_wallet SET balance = ? WHERE user_id = ?";
    private static final String CREATE_SUBSCRIPTION = "INSERT INTO subscriptions " +
            "(start_date, end_date, user_id, magazine_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_USER_SUBSCRIPTIONS_QUERY = "SELECT * FROM subscriptions WHERE user_id = ?";
    private static final String FIND_MAGAZINE_BY_ID_QUERY = "SELECT m.*, c.category_name, p.publisher_name\n" +
            "FROM periodicals.magazines m\n" +
            "JOIN periodicals.categories c\n" +
            "ON c.id = m.category_id\n" +
            "JOIN periodicals.publishers p\n" +
            "ON p.id = m.publisher_id\n" +
            "WHERE m.id = ?";
    private static final String UPDATE_SUBSCRIPTION_STATUS = "UPDATE subscriptions SET status = ? WHERE id = ?";
    private static final String UPDATE_MAGAZINE_QUERY = "UPDATE magazines SET magazine_name = ?, price = ?, " +
            "description = ?, category_id = ?, publisher_id = ? WHERE id = ?";
    private static final String UPDATE_MAGAZINE_WITH_IMAGE_QUERY = "UPDATE magazines SET image_link = ?, " +
            "magazine_name = ?, price = ?, description = ?, category_id = ?, publisher_id = ? WHERE id = ?";
    private static final String INSERT_NEW_MAGAZINE_QUERY = "INSERT INTO magazines VALUES (default, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_MAGAZINE_QUERY = "DELETE FROM magazines WHERE id = ?";
    private static final String UPDATE_USER_STATUS_QUERY = "UPDATE users SET status = ? WHERE id = ?";


    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    public List<Magazine> findAllMagazines(Connection connection, String column, String order, int limit, int offset) {
        List<Magazine> magazines = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        if (column == null || column.isEmpty()) {
            column = "magazine_name";
            order = "";
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(SELECT_ALL_MAGAZINES_WITH_LIMIT_QUERY, column, order, limit, offset));
            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(resultSet.getInt("id"));
                magazine.setMagazineName(resultSet.getString("magazine_name"));
                magazine.setPrice(resultSet.getBigDecimal("price"));
                magazine.setDescription(resultSet.getString("description"));
                magazine.setImageLink(resultSet.getString("image_link"));
                magazine.setCategoryName(resultSet.getString("category_name"));
                magazine.setPublisherName(resultSet.getString("publisher_name"));
                magazines.add(magazine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return magazines;
    }

    public List<Magazine> findAllMagazines(Connection connection) {
        List<Magazine> magazines = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_MAGAZINES_QUERY);
            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(resultSet.getInt("id"));
                magazine.setMagazineName(resultSet.getString("magazine_name"));
                magazine.setPrice(resultSet.getBigDecimal("price"));
                magazine.setDescription(resultSet.getString("description"));
                magazine.setImageLink(resultSet.getString("image_link"));
                magazine.setCategoryName(resultSet.getString("category_name"));
                magazine.setPublisherName(resultSet.getString("publisher_name"));
                magazines.add(magazine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return magazines;
    }

    public List<Magazine> searchMagazines(Connection connection, String searchQuery, String column, String order, int limit, int offset) {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (column == null || column.isEmpty()) {
            column = "magazine_name";
            order = "";
        }
        try {
            statement = connection.prepareStatement(String.format(SEARCH_MAGAZINES_BY_QUERY, column, order, limit, offset));
            int k = 1;
            statement.setString(k++, "%" + searchQuery + "%");
            statement.setString(k++, "%" + searchQuery + "%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(resultSet.getInt("id"));
                magazine.setMagazineName(resultSet.getString("magazine_name"));
                magazine.setPrice(resultSet.getBigDecimal("price"));
                magazine.setDescription(resultSet.getString("description"));
                magazine.setImageLink(resultSet.getString("image_link"));
                magazine.setCategoryName(resultSet.getString("category_name"));
                magazine.setPublisherName(resultSet.getString("publisher_name"));
                magazines.add(magazine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return magazines;
    }

    public List<Category> findAllCategories(Connection connection) {
        List<Category> categories = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_CATEGORIES_QUERY);
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setCategoryUrl(resultSet.getString("url"));
                category.setProductCount(resultSet.getInt("product_count"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return categories;
    }

    public List<Publisher> findAllPublishers(Connection connection) {
        List<Publisher> publishers = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_PUBLISHERS_QUERY);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("publisher_name");
                Publisher publisher = new Publisher(id, name);
                publishers.add(publisher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return publishers;
    }

    public int getCountOfAllMagazines(Connection connection) throws SQLException {
        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(COUNT_ALL_MAGAZINES_QUERY);
            resultSet.next();
            count = resultSet.getInt(1);
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return count;
    }

    public List<Magazine> findMagazinesByCategory(Connection connection, String categoryUrl,
                                                  String column, String order, int limit, int offset) {
        List<Magazine> magazines = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (column == null || column.isEmpty()) {
            column = "magazine_name";
            order = "";
        }
        try {
            statement = connection.prepareStatement(String.format(SELECT_MAGAZINES_BY_CATEGORY_LINK_QUERY, column, order, limit, offset));
            statement.setString(1, categoryUrl);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(resultSet.getInt("id"));
                magazine.setMagazineName(resultSet.getString("magazine_name"));
                magazine.setPrice(resultSet.getBigDecimal("price"));
                magazine.setDescription(resultSet.getString("description"));
                magazine.setImageLink(resultSet.getString("image_link"));
                magazine.setCategoryName(resultSet.getString("category_name"));
                magazine.setPublisherName(resultSet.getString("publisher_name"));
                magazines.add(magazine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return magazines;
    }

    public List<User> findAllUsers(Connection connection, int limit, int offset) {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_ALL_USERS_QUERY, limit, offset));
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getString("salt"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setGender(resultSet.getString("gender"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(Role.fromValue(resultSet.getString("role_name")));
                user.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return users;
    }

    public int getCountOfAllUsers(Connection connection) throws SQLException {
        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(COUNT_ALL_USERS_QUERY);
            resultSet.next();
            count = resultSet.getInt(1);
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return count;
    }

    public User findUserByLogin(Connection connection, String login) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(String.format(FIND_USER_BY_SPECIFIC_COLUMN_QUERY, "login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getString("salt"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setGender(resultSet.getString("gender"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(Role.fromValue(resultSet.getString("role_name")));
                user.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                user.setWallet(new EWallet(user.getId(),
                        resultSet.getBigDecimal("balance")));
                user.setSubscriptions(findUserSubscriptions(connection, user));
                connection.commit();
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return user;
    }

    public List<Subscription> findUserSubscriptions(Connection connection, User user) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(FIND_USER_SUBSCRIPTIONS_QUERY);
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setStartDate(resultSet.getDate("start_date").toLocalDate());
                subscription.setEndDate(resultSet.getDate("end_date").toLocalDate());
                subscription.setStatus(resultSet.getString("status"));
                subscription.setUser(user);
                Magazine magazine = findMagazine(connection, resultSet.getInt("magazine_id"));
                subscription.setMagazine(magazine);
                subscriptions.add(subscription);
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return subscriptions;
    }

    public Magazine findMagazine(Connection connection, int magazineId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Magazine magazine = null;
        try {
            statement = connection.prepareStatement(FIND_MAGAZINE_BY_ID_QUERY);
            statement.setInt(1, magazineId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                magazine = new Magazine();
                magazine.setId(magazineId);
                magazine.setMagazineName(resultSet.getString("magazine_name"));
                magazine.setPrice(resultSet.getBigDecimal("price"));
                magazine.setDescription(resultSet.getString("description"));
                magazine.setImageLink(resultSet.getString("image_link"));
                magazine.setCategoryName(resultSet.getString("category_name"));
                magazine.setPublisherName(resultSet.getString("publisher_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return magazine;
    }

    public User findUserByEmail(Connection connection, String email) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            statement = connection.prepareStatement(String.format(FIND_USER_BY_SPECIFIC_COLUMN_QUERY, "email"));
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setSalt(resultSet.getString("salt"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setGender(resultSet.getString("gender"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(Role.fromValue(resultSet.getString("role_name")));
                user.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
        return user;
    }

    public void createNewUser(Connection connection, User user) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_NEW_USER_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            statement.setString(k++, user.getLogin());
            statement.setString(k++, user.getPassword());
            statement.setString(k++, user.getSalt());
            statement.setString(k++, user.getEmail());
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setString(k++, user.getGender());
            statement.setDate(k++, Date.valueOf(user.getCreationDate()));
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                statement = connection.prepareStatement(CREATE_USER_E_WALLET);
                statement.setInt(1, resultSet.getInt(1));
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
    }

    public void updateWalletBalance(Connection connection, User user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_E_WALLET_BALANCE);
            int k = 1;
            statement.setBigDecimal(k++, user.getWallet().getAmountOfMoney());
            statement.setInt(k++, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(statement);
        }
    }

    public void createNewSubscription(Connection connection, Subscription subscription) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(CREATE_SUBSCRIPTION,
                    statement.RETURN_GENERATED_KEYS);
            int k = 1;
            statement.setDate(k++, Date.valueOf(subscription.getStartDate()));
            statement.setDate(k++, Date.valueOf(subscription.getEndDate()));
            statement.setInt(k++, subscription.getUser().getId());
            statement.setInt(k++, subscription.getMagazine().getId());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                subscription.setId(resultSet.getInt(1));
            }
            subscription.setStatus("active");
            updateWalletBalance(connection, subscription.getUser());
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
    }

    public void updateSubscriptionsStatus(Connection connection, List<Subscription> subscriptions) {
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_SUBSCRIPTION_STATUS);
            for (Subscription subscription : subscriptions) {
                statement.setString(1, subscription.getStatus());
                statement.setInt(2, subscription.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            DBUtils.close(statement);
        }
    }

    public void updateMagazine(Connection connection, Magazine magazine) {
        PreparedStatement statement = null;
        try {
            int k = 1;
            if (magazine.getImageLink() == null) {
                statement = connection.prepareStatement(UPDATE_MAGAZINE_QUERY);
            } else {
                statement = connection.prepareStatement(UPDATE_MAGAZINE_WITH_IMAGE_QUERY);
                statement.setString(k++, magazine.getImageLink());
            }
            statement.setString(k++, magazine.getMagazineName());
            statement.setBigDecimal(k++, magazine.getPrice());
            statement.setString(k++, magazine.getDescription());
            statement.setInt(k++, magazine.getCategoryId());
            statement.setInt(k++, magazine.getPublisherId());
            statement.setInt(k++, magazine.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(statement);
        }
    }

    public void createNewMagazine(Connection connection, Magazine magazine) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_MAGAZINE_QUERY,
                    statement.RETURN_GENERATED_KEYS);
            int k = 1;
            statement.setString(k++, magazine.getMagazineName());
            statement.setBigDecimal(k++, magazine.getPrice());
            statement.setString(k++, magazine.getDescription());
            statement.setString(k++, magazine.getImageLink());
            statement.setInt(k++, magazine.getCategoryId());
            statement.setInt(k++, magazine.getPublisherId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                magazine.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
        }
    }

    public void deleteMagazine(Connection connection, int magazineId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_MAGAZINE_QUERY);
            statement.setInt(1, magazineId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(statement);
        }
    }

    public void updateUserStatus(Connection connection, int userId, String status) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_USER_STATUS_QUERY);
            int k = 1;
            statement.setString(k++, status);
            statement.setInt(k++, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(statement);
        }
    }

}
