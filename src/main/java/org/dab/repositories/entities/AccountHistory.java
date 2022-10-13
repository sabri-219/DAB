package org.dab.repositories.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountHistory {
    String operation;
    Date date;
    int amount;
    int balance;

    public AccountHistory(String operation, int amount, int balance){
        this.operation = operation;
        this.date = new Date();
        this.amount = amount;
        this.balance = balance;
    }
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String d = dateFormat.format(date);
        return operation + String.join("", Collections.nCopies(12 - operation.length(), " ")) + "|" + d + "| "
                + amount + String.join("", Collections.nCopies(19 - Integer.toString(amount).length(), " ")) + "| "
                + balance;
    }
}
