package model.adt;

import java.util.List;
import java.util.Stack;

import exceptions.ADTException;
import exceptions.ToyException;

public interface MyIStack<T> {
    T pop() throws ToyException;

    void push(T e);

    boolean isEmpty();

    List<T> getReversed();

    T peek() throws ADTException;

    public Stack<T> getStack();
}
