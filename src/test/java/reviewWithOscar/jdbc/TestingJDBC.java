package reviewWithOscar.jdbc;

import java.sql.*;

public class TestingJDBC {

    public static void main(String[] args) throws SQLException {
        final String dbUrl = "jdbc:oracle:thin:@54.91.210.3:1521:xe";
        final String dbUsername = "hr";
        final String dbPassword = "hr";

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employees");

        resultSet.next();
        resultSet.next();

        System.out.println("resultSet.getString(1) = " + resultSet.getString(1));

        int i=1;
        while(resultSet.next()){
            System.out.println("resultSet.getString(i) = " + resultSet.getString(i));
            i++;
            if(i==4){
                break;
            }
        }


    }


}
