package org.dab.repositories;

import org.dab.repositories.entities.Account;
import org.dab.repositories.entities.AccountHistory;
import org.dab.repositories.entities.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserAccountRepository {

    Repository repository;

    public UserAccountRepository(){
        repository = MemoryRepository.getInstance();
    }

    public String addAccountAndGetId(String firstName, String lastName){
        String userId = Integer.toString((firstName+lastName).hashCode()); //assume this is weak but it is just for demonstration
        Optional<Object> opt = repository.get(User.class, userId);
        User user;
        if(opt.isPresent()){
            user = (User) opt.get();
        } else{
            user = new User(userId, firstName, lastName);
            repository.save(user);
        }
        String accountId = repository.getId();
        Account account = new Account(accountId, userId);
        user.addAccount(accountId);
        repository.save(account);
        return accountId;
    }

    public boolean containsAccount(String id){
        return repository.contains(Account.class, id);
    }

    public boolean containsUser(String id){
        return repository.contains(User.class, id);
    }

    public void addAmount(String id, int amount){
        repository.addInteger(Account.class, id, amount);
    }

    public boolean subAmount(String id, int amount, int lowering){
        return repository.subInteger(Account.class, id, amount, lowering);
    }

    public List<AccountHistory> getAmountHistory(String id){
        Optional<Object> opt = repository.get(AccountHistory.class, id);
        if(!opt.isPresent()){
            return new LinkedList<AccountHistory>();
        }
        return (List<AccountHistory>) opt.get();
    }
}
