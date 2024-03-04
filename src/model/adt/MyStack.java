package model.adt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import exceptions.ADTException;

public class MyStack<T> implements MyIStack<T> {

    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty");
        return this.stack.pop();

    }

    @Override
    public void push(T e) {
        this.stack.push(e);

    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();

    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T elem : this.stack)
            result.append(elem.toString()).append("\n");
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);
        return "[stack=" + result + "]";
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    public void setStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public T peek() throws ADTException {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty");
        return stack.peek();
    }
}
