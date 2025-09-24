package com.expense.expenseGUI;
import javax.swing.JFrame;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.List;
import com.expense.expenseDAO.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import com.expense.model.expense;
public class enterGUI extends JFrame{
    private JButton category;
    private JButton expenses;
    public enterGUI(){
        initUI();
        setComponents();
        setupEvents();
        


    }
    private void setupEvents(){
        category.addActionListener((e)->{
            new categoryGUI().setVisible(true);
        });
        expenses.addActionListener((e)->{
            new expenseGUI().setVisible(true);
        });
    }
    private void initUI(){
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
    }
    private void setComponents(){
        JPanel panel = new JPanel();
        JPanel btns = new JPanel(new FlowLayout());
        category = new JButton("Category");
        expenses = new JButton("Expenses");
        btns.add(category);
        btns.add(expenses);

        panel.add(btns);
        add(panel,BorderLayout.CENTER);

    }
    
}
class categoryGUI extends JFrame {
    private JTextField category;
    private JButton addButton;
    private DefaultTableModel cattable = new DefaultTableModel(new String[]{"id","category"}, 0);
    
    categoryGUI(){
        setupLayout();
        setupComponents();
        setupListeners();
        loadTable();
    }
    private void loadTable(){
        try{
        List <expense> d = expenseDAO.loadCatTable();
        updateTable(d);}
        catch(Exception e){
            System.out.println("Error");
        }
    }
    private void setupLayout(){
        setTitle("Category");
        setSize(500,500);
        setLocationRelativeTo(null);

    }
    private void setupListeners(){
        addButton.addActionListener((e)->{
            addCategory();
        });

    }
    private void addCategory(){
        try{
        String category_new = category.getText().trim();
        expenseDAO.addCat(category_new);
        loadTable();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Cannot add","Error",JOptionPane.ERROR_MESSAGE);
        }

    }
    private void updateTable(List<expense> d){
        cattable.setRowCount(0);
        for(expense n:d){
            Object[] m={
                n.getId(),
                n.getName()
            };
        
            cattable.addRow(m);
        }

    }
    private void setupComponents(){
        JPanel panel = new JPanel();
        JLabel cat = new JLabel("Category : ");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(5,5,5,5);
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(cat,gbc);
        category= new JTextField();
        gbc.gridx=1;
        category.setPreferredSize(new Dimension(100,20));
        gbc.fill=GridBagConstraints.HORIZONTAL;
        panel.add(category,gbc);
        JPanel btns = new JPanel(new FlowLayout());
        addButton = new JButton("add");
        btns.add(addButton);

        JTable table = new JTable(cattable);
        gbc.gridy=1;
        gbc.gridx=0;
        panel.add(btns);
        add(panel,BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);



    }
    
}
class expenseGUI extends JFrame{
    expenseGUI(){
        setupLayout();
        getCategories();
        initComponents();
        setupListeners();
        loadTable();
        
    }
    private JTextField expense_title;
    private JTextArea expense_area;
    private JButton addButton;
    private JComboBox<String> category;
    private DefaultTableModel expense_table;
    private JTextField expense_amount;


    private String[] columnNames={"id","Name","Descripton","Category","Amount"};
    private void setupListeners(){
        addButton.addActionListener((e)->{
            addExpense();
        });
    }
    private void loadTable(){
        try{
            List <expense> rows= expenseDAO.getExpenseTable();
            updateTable(rows);
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Error Loading table","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateTable(List<expense> rows){
        expense_table.setRowCount(0);
        for(expense t: rows){
            Object [] m = {
                t.getId(),
                t.getName(),
                t.getDescription(),
                t.getCategory(),
                t.getAmount(),
            };
            expense_table.addRow(m);
        }

    }
    private void addExpense(){

        String expense_name = expense_title.getText().trim();
        String expense_description = expense_area.getText().trim();
        String category_name = (String)category.getSelectedItem(); 
        String amount = (String) expense_amount.getText().trim();
        if (expense_name.isEmpty()){
            JOptionPane.showMessageDialog(this,"Fill all the boxes","Missing info",JOptionPane.WARNING_MESSAGE);
        }
        try{
            int row=expenseDAO.addExpense(expense_name,expense_description,category_name,amount);
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this,"Error","error",JOptionPane.ERROR_MESSAGE);
        }
        loadTable();




    }
    private void getCategories(){
        try{
            List <expense> category_options = expenseDAO.loadCatTable();
            category = new JComboBox<>();
            for (expense t:category_options){
                category.addItem(t.getName());
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Couldn't fetch categories","Category error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setupLayout(){
        setTitle("Expenses");
        setSize(500,500);
        setLocationRelativeTo(null);
        
    }
    private void initComponents(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(new JLabel("Expense : "),gbc);
        gbc.gridx=1;
        expense_title = new JTextField();
        expense_title.setPreferredSize(new Dimension(200,20));
        gbc.fill=GridBagConstraints.HORIZONTAL;
        panel.add(expense_title,gbc);
        gbc.gridy=1;
        gbc.gridx=0;
        panel.add(new JLabel("Description :"),gbc);
        expense_area=new JTextArea();
        gbc.gridy=1;
        gbc.gridx=1;
        expense_area.setPreferredSize(new Dimension(200,100));
        panel.add(expense_area,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(new JLabel("Select a category :"),gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        panel.add(category,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(new JLabel("Enter Amount : "),gbc);
        expense_amount = new JTextField();
        expense_amount.setPreferredSize(new Dimension(100,20));
        gbc.gridx=1;
        panel.add(expense_amount,gbc);
        gbc.gridx=0;
        JPanel btns = new JPanel(new FlowLayout());
      
        addButton = new JButton("Add Expense");
        btns.add(addButton);
        gbc.gridy=4;
        panel.add(btns,gbc);
        add(panel,BorderLayout.NORTH);
        expense_table = new DefaultTableModel(columnNames,0);
        JTable table = new JTable(expense_table);
        add(new JScrollPane(table),BorderLayout.CENTER);

    }

}

