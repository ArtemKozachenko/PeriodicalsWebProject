package com.periodicals.manager;

import java.sql.SQLException;

public class DBException extends Exception {
    private static final long serialVersionUID = 1342487619677883255L;

    public DBException(String s, SQLException e) {
        super(s, e);
    }
}
