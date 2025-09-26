package com.expense.expenseDAO;
import com.expense.model.expense;
import java.util.List;
import java.util.*;
import com.expense.util.DatabaseConnection;
import java.sql.*;
public class expenseDAO {
    private static final String FETCH = "select * from category";
    private static final String FETCH_EXPENSE="select * from expenses";
    private static final String ADD = "insert into category (name) values(?)";
    private static final String CATEGORY_ID="select id from category where name=?";
    private static final String CATEGORY_NAME = " select name from category where id=?";
    private static final String ADD_EXPENSE = "insert into expenses(expense,description,category_id,amount) values(?,?,?,?)";
    private static final String DELETE_EXPENSE="delete from expenses where id =?";
    private static final String UPDATE_CATEGORY="update category set name =? where id =?";
    private static final String DELETE_CATEGORY="delete from category where id =?";
    private static final String UPDATE_EXPENSE="update expenses set expense=? ,description=?,category_id=?,amount=? where id=?";
    public static int updateCategory(int id,String name) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_CATEGORY);
        )
        {
            stmt.setString(1,name);
            stmt.setInt(2,id);
            return stmt.executeUpdate();
        }

    }
    public static int deleteCategory(int id) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(DELETE_CATEGORY);
        )
        {
            stmt.setInt(1,id);
            return stmt.executeUpdate();
        }
    }
    private static int getCategoryID(String category) throws SQLException{

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(CATEGORY_ID);
        )
        {
            stmt.setString(1,category);
            ResultSet rs= stmt.executeQuery();
            int id=0;
            while(rs.next()){
                id=rs.getInt("id");
            }
            return id;
        }
    }
    public static int updateExpense(String name,String description,String category,String amount,int id) throws SQLException{
        int category_id = getCategoryID(category);
        
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_EXPENSE);
        ){
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, category_id);
            stmt.setString(4, amount);
            stmt.setInt(5, id);
            return stmt.executeUpdate();
        }
    }
    public static int deleteExpense(int id) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_EXPENSE);
        ){
           
            stmt.setInt(1,id);
            int rowAffected=stmt.executeUpdate();
            return rowAffected;
        }
        
    }
    public static List<expense> getExpenseTable() throws SQLException{
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(FETCH_EXPENSE);
        )
        {
            List<expense> tables = new ArrayList<>();
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
                expense t = new expense(rs.getInt("id"),rs.getString("expense"),rs.getString("description"),loadCategory(rs.getInt("category_id")),rs.getString("amount"));
                System.out.println(t);
                tables.add(t);
            }
            return tables;
        }
    } 
    private static String loadCategory(int id) throws SQLException{
        String category_name="";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(CATEGORY_NAME); 
        )
        {
            stmt.setInt(1,id);
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
            category_name=rs.getString("name");
            }

        }
        return category_name;
    }
    public static int addExpense(String name,String description,String category,String amount) throws SQLException{
        int rowAffected=0;
        int category_id=0;
        try
            (Connection conn = DatabaseConnection.getConnection();
            
            PreparedStatement stmt2 = conn.prepareStatement(CATEGORY_ID);
            )
        {
            stmt2.setString(1,category);
            ResultSet rs1= stmt2.executeQuery();
            
            while(rs1.next()){
                category_id=rs1.getInt("id");
            }
        }try(
            Connection conn =DatabaseConnection.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(ADD_EXPENSE);
        ){
            stmt1.setString(1,name);
            stmt1.setString(2,description);
            stmt1.setInt(3,category_id);
            stmt1.setString(4,amount);
            rowAffected= stmt1.executeUpdate();
        }
            return rowAffected;
        }

    public static List<expense> loadCatTable() throws SQLException {
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(FETCH);
        ) {
        List <expense> d = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            expense t = new expense(rs.getInt("id"),rs.getString("name"));
            System.out.println(t.getId()+' '+t.getName());
            d.add(t);
        }
        return d;
        }
        
    

    }
    public static int addCat(String category_new) throws SQLException{
        int rowAffected=0;
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(ADD);
        )
        {
            stmt.setString(1,category_new);
            rowAffected = stmt.executeUpdate();
        }
        return rowAffected;
    }

}

