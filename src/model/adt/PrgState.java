package model.adt;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.ToyException;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

public class PrgState {

    MyIStack<IStmt> exeStack;
    MyIDict<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIDict<StringValue, BufferedReader> fileTable;
    MyIHeap<Value> heapTable;
    private static Integer lastId = 1;
    private Integer id;

    public PrgState(MyIStack<IStmt> exeStack, MyIDict<String, Value> symTable, MyIList<Value> out,
            MyIDict<StringValue, BufferedReader> fileTable, MyIHeap<Value> heapTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.id = 1;

        this.fileTable = fileTable;
        this.heapTable = heapTable;
    }

    public synchronized void setId() {
        lastId++;
        id = lastId;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDict<String, Value> symTable, MyIList<Value> out,
            IStmt originalProgram,
            MyIDict<StringValue, BufferedReader> fileTable, MyIHeap<Value> heapTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.id = 1;
        if (originalProgram != null)
            this.exeStack.push(originalProgram);
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDict<String, Value> symTable, MyIList<Value> out,
            IStmt originalProgram) {
        this.exeStack = new MyStack<IStmt>();
        this.symTable = symTable;
        this.out = out;
        this.id = 1;

        if (originalProgram != null)
            this.exeStack.push(originalProgram);

    }

    public MyIDict<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDict<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDict<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    @Override
    public String toString() {

        return "\nID:\n" + id + "\nExeStack:\n" + exeStack.toString() + "\nSymTable\n" + symTable.toString() + "\nOut\n"
                + out.toString()
                + "\nFileTable\n" + fileTable.fileToString() + "HeapTable\n" + heapTable.toString();
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setFileTable(MyIDict<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public MyIHeap<Value> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(MyIHeap<Value> heapTable) {
        this.heapTable = heapTable;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws ToyException, IOException {
        // if (this.exeStack.isEmpty())
        // throw new ToyException("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public Integer getId() {
        return id;
    }

}
