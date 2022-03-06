package reviewWithOscar.jdbc;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestWithOracle {

    Connection connection;
    Statement statement;
    ResultSet resultSet;


    @BeforeMethod
    public void connectToDB() throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@54.91.210.3:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";
        String query = "select first_name,last_name,salary from employees";

            //create connection


            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

    }

    @AfterMethod
    public void closeDB()  {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void connectionTest() throws SQLException {

        //resultSet.next();//moved the cursor to the next row
        //resultSet.getObject(); -- brings the infor in that cell;


        while(resultSet.next()){
            System.out.println(resultSet.getObject(1) + " " +resultSet.getObject(1) + " " + resultSet.getObject(1));
        }
    }

    @Test
    public void connectionTest2() throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@54.91.210.3:1521:xe";
        String dbUserName = "hr";
        String dbPassWord = "hr";
        String query = "select first_name,last_name,salary from employees";
        //  resultSet.next()  : gets the cursor to the next row

        //  resultSet.getObject(1) :  brings the info in that cell

        while (resultSet.next()){
            System.out.println(resultSet.getObject(1)+"|"+resultSet.getObject(2)+"|"+resultSet.getObject(3));
        }
    }

    @Test
    public void verifyExample() throws SQLException {

        //get Steven King salary and verify that is 24800

        resultSet.next();
        int actualSalary = resultSet.getInt(3);
        int expectedSalary = 24000;

        System.out.println("actualSalary = " + actualSalary);
        System.out.println("expectedSalary = " + expectedSalary);

    }

    @Test
    public void ListOfMapExample() throws SQLException {
        Map<String, Object> rowOneData = new HashMap<>(); //insertion order is not preserved
        rowOneData.put("first_name", "Steven");
        rowOneData.put("last_name", "King");
        rowOneData.put("salary", "24000");

        System.out.println("rowOneData = " + rowOneData);

        Map<String, Object> rowTwoData = new HashMap<>(); //insertion order is not preserved
        rowTwoData.put("first_name", "Neena");
        rowTwoData.put("last_name", "Kochar");
        rowTwoData.put("salary", "17000");

        System.out.println("rowTwoData = " + rowTwoData);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(rowOneData);
        list.add(rowTwoData);

        System.out.println(list.toString());

        //get Nina's salary

        System.out.println("Ninna's salary: " + list.get(1).get("salary"));

    }

    @Test
    public void metaDataExample() throws SQLException {

        DatabaseMetaData databaseMetaData = connection.getMetaData();

        System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());
        System.out.println("databaseMetaData.getDatabaseProductName() = " + databaseMetaData.getDatabaseProductName());
        System.out.println("databaseMetaData.getDatabaseProductVersion() = " + databaseMetaData.getDatabaseProductVersion());

        ResultSetMetaData rsMD = resultSet.getMetaData();

        int columnCount = rsMD.getColumnCount();

        String columnName = rsMD.getColumnName(1);

        System.out.println("columnCount = " + columnCount);
        System.out.println("columnName = " + columnName);

    }

    @Test
    public void dynamicMap() throws SQLException {
        ResultSetMetaData rsMD = resultSet.getMetaData();

        List<Map<String, Object>> list = new ArrayList<>();

        while(resultSet.next()){
            Map<String, Object> rowMap = new HashMap<>();
            for(int i=1; i<= rsMD.getColumnCount();i++){
                rowMap.put(rsMD.getColumnName(i), resultSet.getObject(i));
            }
            list.add(rowMap);
        }

        for (Map<String, Object> row : list) {
            System.out.println("row" + row);
        }


    }
}
