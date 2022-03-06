package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamic_list {

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
        ResultSet resultSet = statement.executeQuery("Select * from countries");

        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();


        //list for keeping all rows of maps
        List<Map<String, Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount = rsMetaData.getColumnCount();

        while(resultSet.next()){
            Map<String, Object> row = new HashMap<>();
            for (int j = 1; j <= colCount; j++) {
                row.put(rsMetaData.getColumnName(j), resultSet.getString(j));
            }

            //add map to the list
            queryData.add(row);
        }

        for (Map<String, Object> row : queryData) {
            System.out.println(row);
        }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
