package com.expense;
import com.expense.expenseGUI.*;
import com.expense.util.DatabaseConnection;

import java.sql.SQLException;
public class Main{
    public static void main(String args[]){
        System.out.println("Hello world");
        try{
            new DatabaseConnection().getConnection();
            System.out.println("Database Connection successful");
        }
        catch (SQLException e){
            System.out.println("Database Connection failed");
        }
       new enterGUI().setVisible(true);

    }
}