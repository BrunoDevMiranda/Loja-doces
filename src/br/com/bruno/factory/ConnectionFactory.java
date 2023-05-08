package br.com.bruno.factory;

import java.sql.*;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {

            final String url = "jdbc:mysql://localhost/lojafini?useTimeZone=true&serverTimezone=UTC";
            final String user = "root";
            final String password = "643499br";
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
          return  DriverManager.getConnection(url, user, password);
        } catch (SQLException  e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn) throws DbException {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement ps) throws DbException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) throws DbException {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
