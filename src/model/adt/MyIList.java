package model.adt;

import java.util.List;

public interface MyIList<T> {
    void add(T e);

    void clear();

    public List<T> getList();
}