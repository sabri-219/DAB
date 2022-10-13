package org.dab.repositories;

import java.util.Optional;

public interface Repository {
    public String getId();
    public void save(Object obj);
    public boolean contains(Class cls, String id);
    public Optional<Object> get(Class cls, String id);
    public void addInteger(Class cls, String id, int value);
    public boolean subInteger(Class cls, String id, int value, int lowering);
}
