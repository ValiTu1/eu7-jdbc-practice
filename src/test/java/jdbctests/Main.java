package jdbctests;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:oracle:thin:@44.203.111.61:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement();

        //run query and get the result in resulset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

/*        //move pointer to the first row
        resultSet.next();
        //System.out.println(resultSet.getString("region_name"));
        System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
        //move pointer to second row
        resultSet.next();
        //System.out.println(resultSet.getString("region_name"));
        System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));

        //"1 - Europe"
        //"2- Americas"*/

        while(resultSet.next()){
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2)+ " - " + resultSet.getString(3)+ " - " + resultSet.getString(4));
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }
}
