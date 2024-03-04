package model.adt;

import java.util.Map;

import exceptions.ADTException;
import exceptions.ToyException;

public interface MyIHeap<V> {
    int put(V v) throws ToyException;

    void setContent(Map<Integer, V> map);

    boolean isDefined(Integer i);

    V lookUp(Integer i) throws ADTException;

    void update(Integer i, V v);

    Map<Integer, V> getContent();
}
