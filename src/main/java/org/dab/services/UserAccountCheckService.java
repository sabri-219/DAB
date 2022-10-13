package org.dab.services;

import org.dab.repositories.MemoryRepository;
import org.dab.repositories.UserAccountRepository;

public class UserAccountCheckService {

    UserAccountRepository repository;
    public UserAccountCheckService(){
        repository = new UserAccountRepository();
    }

    public boolean validate(String accountCode){
        return (accountCode.matches("[A-Za-z0-9]+")) && repository.containsAccount(accountCode);
    }
}
