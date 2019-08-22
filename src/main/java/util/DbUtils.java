package main.java.util;

import com.sun.rowset.CachedRowSetImpl;
import org.apache.poi.ss.formula.functions.T;
import org.sqlite.SQLiteConnection;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DbUtils {

    public static Connection conn;
    private static ResultSet rs;
    public static Statement statement;
    private static PreparedStatement pst;
    private static CachedRowSet crs;

    //TODO: open connection to db
    public static  void dbConnect(){
        try{
           conn =  DriverManager.getConnection(DbProps.connection_str.getProp());
            if (conn != null){
                System.out.println("connected to db");
            }
        }catch (Exception e){
            System.out.println("error in connection "+e.getLocalizedMessage());
        }


    }



    //TODO : close and exit database
    public static void dbDisconnect(){
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.closeOnCompletion();
                }
                if (statement != null && !statement.isClosed()) {
                    statement.closeOnCompletion();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }


    /**
     * use this method to perform your select statements..
     * @param query SQL statement to execute
     * @return : resultSet..
     */
    public static ResultSet executeQuery(String query, List<?> values){
        dbConnect();
        try {
            pst = conn.prepareStatement(query);

            if (values != null && !values.isEmpty()){
                int position = 1;
                for (Object value : values) {
                    pst.setObject(position, value);
                    position++;
                }
            }

            rs = pst.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            System.out.println(crs.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbDisconnect();
        }

        return crs;
    }


    /**use to perform updates and delete operations
     *
     * @param query : sql statement to run
     */
    public static void executeUpdate(String query, List<?> values){
        dbConnect();
        try {
            pst = conn.prepareStatement(query);

            if(values != null && !values.isEmpty()){
                int position = 1;
                for (Object value : values) {
                    pst.setObject(position,value);
                    position++;
                }
            }
            pst.execute();
            System.out.println("updated data successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbDisconnect();
        }

}




    }
