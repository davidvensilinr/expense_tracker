package com.expense.util;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import io.github.cdimascio.dotenv.Dotenv;
public class DatabaseConnection{
    private static final String URL= "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USER="root";
    private static final String PASSWORD = Dotenv.load().get("sql_pass");
    static{
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
    catch(ClassNotFoundException e){
        System.out.print("jdbc not found");
    }
}
   
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}