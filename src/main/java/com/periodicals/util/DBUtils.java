package com.periodicals.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private static DBUtils instance;
    private final DataSource ds;

    private DBUtils() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/PeriodicalsDB");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot obtain a data source", ex);
        }
    }

    public static synchronized DBUtils getInstance() {
        if (instance == null) {
            instance = new DBUtils();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(AutoCloseable as) {
        if (as != null) {
            try {
                if (as instanceof Connection && !((Connection) as).getAutoCommit()) {
                    ((Connection) as).setAutoCommit(true);
                }
                as.close();
            } catch (Exception e) {
                // write to log
            }
        }
    }

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                // write to log
            }
        }
    }
}
