package ku.cs.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {

    public static Connection ConnectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/sugarcane_sa";
            Connection con = DriverManager.getConnection(url, "root", "");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

