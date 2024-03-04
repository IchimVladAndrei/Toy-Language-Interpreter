package model.adt;

import java.util.HashMap;
import java.util.Map;

import exceptions.ADTException;
import exceptions.ToyException;

public class MyHeap<V> implements MyIHeap<V> {
    public static int nextAddress = 0;
    protected Map<Integer, V> map;

    public MyHeap() {
        this.map = new HashMap<Integer, V>();
    }

    @Override
    public int put(V v) throws ToyException {
        map.put(nextFreeAddress(), v);
        return nextAddress;
    }

    private int nextFreeAddress() {
        nextAddress++;
        return nextAddress;
    }

    @Override
    public void setContent(Map<Integer, V> map) {
        this.map = map;
    }

    @Override
    public Map<Integer, V> getContent() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Integer key : map.keySet())
            result.append(key).append("->").append(map.get(key).toString()).append("\n");
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return "[" + result + "]";

    }

    @Override
    public boolean isDefined(Integer i) {
        return map.containsKey(i);
    }

    @Override
    public V lookUp(Integer i) throws ADTException {
        return map.get(i);

    }

    @Override
    public void update(Integer i, V v) {
        map.put(i, v);

    }

}
