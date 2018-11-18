/**
 * @Author      : Milko Del Castillo
 * @Version     : v. 1.0
 * @Since       : 11/09/2018
 * FileName     : dbConnection.java
 * Description  : This class handles the connection with DAtaBase
 */

package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String USERNAME="dbuser";
    private static final String PASSWORD = "dbpassword";
    private static final String CONN= "jdbc:mysql://localhost/login";
    private static final String SQCONN = "jdbc:sqlite:test1.sqlite";

    //connect to Date base , throw a exception if
    public static Connection getConnection() throws SQLException {

        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN);
        }
        catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
