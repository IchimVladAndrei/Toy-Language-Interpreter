package model.adt;

import java.util.Map;
import exceptions.ADTException;

public interface MyIDict<K, V> {
    void put(K k, V v) throws ADTException;

    void update(K k, V newV) throws ADTException;

    boolean isDefined(K k);

    V lookUp(K k);

    void remove(K k);

    Map<K, V> getContent();

    String fileToString();

    MyIDict<K, V> copy() throws ADTException;

}
