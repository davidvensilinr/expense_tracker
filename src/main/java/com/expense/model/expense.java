package com.expense.model;

public class expense {
    private int id;
    private String name;
    private String amount;
    private String description;
    private String category;
    public String getAmount(){
        return this.amount;
    }
    public String getCategory(){
        return this.category;
    }
    public String getDescription(){
        return this.description;
    }
    public expense(){
        
    }
    public expense(int id,String name){
        this.id =id;
        this.name=name;
    }
    public expense (int id,String name,String description,String category,String amount){
        this.id=id;
        this.name =name;
        this.description=description;;
        this.category=category;
        this.amount=amount;
    }
    public int getId(){
        return this.id;

    }
    public String getName(){
        return this.name;
    }
}
