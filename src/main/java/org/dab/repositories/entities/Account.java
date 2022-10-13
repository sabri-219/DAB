package org.dab.repositories.entities;

public class Account {

    private String id;
    private int amount;
    private String userId;

    public Account(String id, String userId){
        this.id = id;
        this.amount = 0;
        this.userId = userId;
    }

    public String getId(){
        return id;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
