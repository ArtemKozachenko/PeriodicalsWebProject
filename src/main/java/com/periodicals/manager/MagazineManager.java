package com.periodicals.manager;

import com.periodicals.bean.Magazine;
import com.periodicals.util.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MagazineManager {
    private static MagazineManager instance;
    private final DBManager dbManager;

    public static synchronized MagazineManager getInstance() {
        if (instance == null) {
            instance = new MagazineManager();
        }
        return instance;
    }

    private MagazineManager() {
        dbManager = DBManager.getInstance();
    }

    public List<Magazine> findAllMagazines(String orderByColumn, String order, int limit, int offset) throws SQLException {
        List<Magazine> magazines;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            magazines = dbManager.findAllMagazines(connection, orderByColumn, order, limit, offset);
        } finally {
            DBUtils.close(connection);
        }
        return magazines;
    }

    public List<Magazine> findAllMagazines() throws SQLException {
        List<Magazine> magazines;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            magazines = dbManager.findAllMagazines(connection);
        } finally {
            DBUtils.close(connection);
        }
        return magazines;
    }

    public List<Magazine> findMagazinesBySearchQuery(String searchQuery, String orderByColumn, String order, int limit, int offset) throws SQLException {
        List<Magazine> magazines;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            magazines = dbManager.searchMagazines(connection, searchQuery, orderByColumn, order, limit, offset);
        } finally {
            DBUtils.close(connection);
        }
        return magazines;
    }

    public int getCountOfAllMagazines() throws DBException {
        int count = 0;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            count = dbManager.getCountOfAllMagazines(connection);
        } catch (SQLException e) {
            //1 log error ...
            //2 rollback if exists
            throw new DBException("Cannot get count ...", e);
        } finally {
            DBUtils.close(connection);
        }
        return count;
    }

    public List<Magazine> findMagazinesByCategory(String categoryUrl,
                                                  String orderByColumn, String order, int limit, int offset) throws SQLException {
        List<Magazine> magazines;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            magazines = dbManager.findMagazinesByCategory(connection, categoryUrl,
                    orderByColumn, order, limit, offset);
        } finally {
            DBUtils.close(connection);
        }
        return magazines;
    }

    public Magazine findMagazine(int magazineId) throws SQLException {
        Magazine magazine;
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            magazine = dbManager.findMagazine(connection, magazineId);
        } finally {
            DBUtils.close(connection);
        }
        return magazine;
    }

    public void updateMagazine(Magazine magazine) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.updateMagazine(connection, magazine);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void createNewMagazine(Magazine magazine) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.createNewMagazine(connection, magazine);
        } finally {
            DBUtils.close(connection);
        }
    }

    public void deleteMagazine(int magazineId) throws SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            dbManager.deleteMagazine(connection, magazineId);
        } finally {
            DBUtils.close(connection);
        }
    }
}
