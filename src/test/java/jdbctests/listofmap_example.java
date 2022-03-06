package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class listofmap_example {

    String dbUrl = "jdbc:oracle:thin:@44.203.111.61:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void MetadataExample() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resulset object
        ResultSet resultSet = statement.executeQuery("Select * from regions");

        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //list for keeping all rows of maps
        List <Map<String, Object>> queryData = new ArrayList<>();


        Map<String, Object> row1 = new HashMap<>();
        row1.put("first_name", "Steven");
        row1.put("last_name", "King");
        row1.put("salary", 24000);
        row1.put("job_id", "AD_PRESS");

        System.out.println(row1.toString());

        Map<String, Object> row2 = new HashMap<>();
        row2.put("first_name", "Nina");
        row2.put("last_name", "Kochar");
        row2.put("salary", 17000);
        row2.put("job_id", "AD_VP");

        System.out.println(row2.toString());

        System.out.println(row2.get("salary"));

        //adding rows to my list
        queryData.add(row1);
        queryData.add(row2);

        //get the steven last name directly from the list
        System.out.println(queryData.get(0).get("last_name"));

        System.out.println(queryData.toString());





        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void MetadataExample2() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resulset object
        ResultSet resultSet = statement.executeQuery("Select first_name, last_name,salary,job_id from employees");

        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();



        //list for keeping all rows of maps
        List <Map<String, Object>> queryData = new ArrayList<>();

        //geting the number of rows
        resultSet.last();
        int noOfRows = resultSet.getRow();

        //move before the first row
        resultSet.beforeFirst();

        for(int i=0;i<noOfRows;i++){
            resultSet.next();
            Map<String, Object> row = new HashMap<>();
            for(int j=1;j<= rsMetaData.getColumnCount();j++){
                row.put(rsMetaData.getColumnName(j), resultSet.getString(j));
            }
            queryData.add(row);
        }

        System.out.println(queryData);
        /*//move to first row

        resultSet.next();
        Map<String, Object> row1 = new HashMap<>();
        row1.put(rsMetaData.getColumnName(1), resultSet.getString(1));
        row1.put(rsMetaData.getColumnName(2), resultSet.getString(2));
        row1.put(rsMetaData.getColumnName(3), resultSet.getString(3));
        row1.put(rsMetaData.getColumnName(4), resultSet.getString(4));

        //move to second row
        resultSet.next();
        System.out.println(row1.toString());
        Map<String, Object> row2 = new HashMap<>();
        row2.put(rsMetaData.getColumnName(1), resultSet.getString(1));
        row2.put(rsMetaData.getColumnName(2), resultSet.getString(2));
        row2.put(rsMetaData.getColumnName(3), resultSet.getString(3));
        row2.put(rsMetaData.getColumnName(4), resultSet.getString(4));

        System.out.println(row2.toString());

        //System.out.println(row2.get("salary"));

        //adding rows to my list
        queryData.add(row1);
        queryData.add(row2);

        //get the steven last name directly from the list
        //System.out.println(queryData.get(0).get("last_name"));*/





        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
