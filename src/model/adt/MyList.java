package model.adt;

import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList() {
        this.list = new LinkedList<T>();
    }

    @Override
    public void add(T e) {
        this.list.add(e);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T elem : this.list)
            result.append(elem.toString()).append("\n");
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return "[" + list + "]";
    }

}
