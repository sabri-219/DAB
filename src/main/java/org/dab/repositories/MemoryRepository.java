package org.dab.repositories;

import org.dab.repositories.entities.Account;
import org.dab.repositories.entities.AccountHistory;
import org.dab.repositories.entities.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryRepository implements Repository {
    private Map<String, Account> accounts;
    private Map<String, User> users;
    Map<String, List<AccountHistory>> accountsHistory;
    private ReadWriteLock rwLock;
    private Lock readLock;
    private Lock writeLock;
    private AtomicInteger idSequences;

    private MemoryRepository(){
        accounts = new HashMap<String, Account>();
        users = new HashMap<String, User>();
        accountsHistory = new HashMap<String, List<AccountHistory>>();
        rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
        idSequences = new AtomicInteger(100);
    }

    private static class MemoryRepositoryHolder{
        private final static MemoryRepository instance = new MemoryRepository();
    }

    public static Repository getInstance() {
        return MemoryRepositoryHolder.instance;
    }

    @Override
    public String getId(){
        int id = idSequences.addAndGet(1);
        return Integer.toString(id);
    }

    @Override
    public void save(Object obj) {
        if(obj == null) return;
        writeLock.lock();
        try{
            if(obj instanceof Account){
                Account acc = (Account) obj;
                accounts.put(acc.getId(), acc);
                AccountHistory histo = new AccountHistory("creation", 0, 0);
                List<AccountHistory> histoList = new LinkedList<AccountHistory>();
                histoList.add(histo);
                accountsHistory.put(acc.getId(), histoList);
            } else if(obj instanceof User) users.put(((User) obj).getId(), ((User) obj));
        }finally{
            writeLock.unlock();
        }
    }

    @Override
    public boolean contains(Class cls, String id) {
        if(cls == null || id == null) return false;
        boolean ret = false;
        readLock.lock();
        try{
            if(cls.equals(Account.class)) ret = accounts.containsKey(id);
            else if(cls.equals(User.class)) ret = users.containsKey(id);
        }finally{
            readLock.unlock();
        }
        return ret;
    }

    @Override
    public Optional<Object> get(Class cls, String id) {
        if(cls == null || id == null) return Optional.ofNullable(null);
        Object obj = null;
        readLock.lock();
        try{
            if(cls.equals(Account.class)) obj = accounts.get(id);
            else if(cls.equals(User.class)) obj = users.get(id);
            else if(cls.equals(AccountHistory.class)) obj = accountsHistory.get(id);
        }finally{
            readLock.unlock();
        }
        return Optional.ofNullable(obj);
    }

    @Override
    public void addInteger(Class cls, String id, int value) {
        if(cls == null || id == null) return;
        writeLock.lock();
        try{
            if(cls.equals(Account.class)){
                Account acc = accounts.get(id);
                int oldAmount = acc.getAmount();
                acc.setAmount(acc.getAmount() + value);
                int newAmount = acc.getAmount();
                List<AccountHistory> histoList = accountsHistory.get(id);
                if(histoList == null) {
                    histoList = new LinkedList<AccountHistory>();
                    accountsHistory.put(id, histoList);
                }
                histoList.add(new AccountHistory("deposit", value, newAmount));
            }
        }finally{
            writeLock.unlock();
        }
    }

    @Override
    public boolean subInteger(Class cls, String id, int value, int lowering) {
        if(cls == null || id == null) return false;
        boolean ret = false;
        writeLock.lock();
        try{
            if(cls.equals(Account.class)){
                Account acc = accounts.get(id);
                if(acc.getAmount() - value >= lowering){
                    acc.setAmount(acc.getAmount() - value);
                    ret = true;
                } else {
                    ret = false;
                }
                List<AccountHistory> histoList = accountsHistory.get(id);
                if(histoList == null) {
                    histoList = new LinkedList<AccountHistory>();
                    accountsHistory.put(id, histoList);
                }
                histoList.add(new AccountHistory("withdrawal", value, acc.getAmount()));
            }
        }finally{
            writeLock.unlock();
        }
        return ret;
    }
}
