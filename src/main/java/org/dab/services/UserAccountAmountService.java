package org.dab.services;

import org.dab.repositories.UserAccountRepository;

public class UserAccountAmountService {

    UserAccountRepository repository;
    public UserAccountAmountService(){
        repository = new UserAccountRepository();
    }
    public void addAmount(String accountId, int amount){
        repository.addAmount(accountId, amount);
    }

    public boolean subAmount(String accountId, int amount){
        return repository.subAmount(accountId, amount, 0);
    }
}
