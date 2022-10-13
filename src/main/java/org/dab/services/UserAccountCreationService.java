package org.dab.services;

import org.dab.repositories.UserAccountRepository;

public class UserAccountCreationService {

    UserAccountRepository repository;
    public UserAccountCreationService(){
        repository = new UserAccountRepository();
    }

    public String createAccount(String firstName, String lastName){
        return repository.addAccountAndGetId(firstName, lastName);
    }
}
