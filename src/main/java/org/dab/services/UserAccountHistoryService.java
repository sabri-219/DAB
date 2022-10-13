package org.dab.services;

import org.dab.repositories.UserAccountRepository;
import org.dab.repositories.entities.AccountHistory;

import java.util.List;
import java.util.stream.Collectors;

public class UserAccountHistoryService {
    UserAccountRepository repository;
    public UserAccountHistoryService(){
        repository = new UserAccountRepository();
    }

    public String getHistory(String id){
        List<AccountHistory> histo = repository.getAmountHistory(id);
        String lineSeparator = System.getProperty("line.separator");
        String data = "operation   |       date        |       amount       |       balance       " + lineSeparator;
        data += histo.stream().map(o -> o.toString()).collect(Collectors.joining(lineSeparator));
        return data;
    }
}
