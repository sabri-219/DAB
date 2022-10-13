package org.dab.repositories.entities;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Set<String> accountsId;

    public User(String id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        accountsId = new HashSet<String>();
    }

    public boolean addAccount(String id){
        return accountsId.add(id);
    }

    public String getId(){
        return id;
    }
}
