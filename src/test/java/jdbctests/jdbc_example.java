package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl = "jdbc:oracle:thin:@44.203.111.61:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resulset object
        ResultSet resultSet = statement.executeQuery("Select * from departments");

        //hot to find how many rows we have for the query
        //go to last row
        /*resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println(rowCount);

        resultSet.beforeFirst();*/
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        resultSet = statement.executeQuery("Select * from regions");
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void Metadata() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resulset object
        ResultSet resultSet = statement.executeQuery("Select * from regions");


        //get the database related inside the dbMetadata
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("User= " + dbMetaData.getUserName());
        System.out.println("Database Product Name= " + dbMetaData.getDatabaseProductName());
        System.out.println("Database Product Version= " + dbMetaData.getDatabaseProductVersion());
        System.out.println("Driver Name= " + dbMetaData.getDriverName());
        System.out.println("Driver Version= " + dbMetaData.getDriverVersion());

        //get the resultSet metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //how many columns we have?
        int colCount = rsMetaData.getColumnCount();
        System.out.println(colCount);

        //column names
        //System.out.println("1st columns name = " + rsMetaData.getColumnName(1));
        //System.out.println("2nd columns name = " + rsMetaData.getColumnName(2));

        //print all the column names dinamically

        int i=1;
        while(i<= colCount){
            System.out.println(rsMetaData.getColumnName(i));
            i++;
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
