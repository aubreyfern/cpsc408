package com.company;
import java.sql.*;

public class DBConfig {
    private Connection pgSqlConnection = null;
    public static Connection getMyConnection()
    {
        Connection mysqlConnection = null;
        try
        {
            //returns the Class object associated with the class or interface with the given
            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = "jdbc:mysql://104.199.117.142:3306/cpsc408";
            mysqlConnection = DriverManager.getConnection(connectionURL, "aub", "cpsc408");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return mysqlConnection;
    }

    public static void displayDbProperties(Connection con) {
        DatabaseMetaData dm;
        ResultSet rs;

        try
        {
            if(con != null)
            {
                dm = con.getMetaData();
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: "+ dm.getDriverName());
                System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                System.out.println("Available Catalogs ");
                rs = dm.getCatalogs();

                while(rs.next()){
                    System.out.println("\tcatalog: "+ rs.getString(1));
                }
                rs.close();
                con.close();
            }
            else
                System.out.println("Error: No active Connection");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
