package model.statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import exceptions.FileIOException;
import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class OpenRFile implements IStmt {
    IExpression expr;

    public OpenRFile(IExpression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value val = expr.eval(symTable, heapTable);
        if (val.getType().equals(new StringType())) {
            StringValue str = (StringValue) val;
            if (fileTable.isDefined(str))
                throw new FileIOException("File is already defined");
            else {

                BufferedReader reader = new BufferedReader(new FileReader(str.getTxt()));
                fileTable.put(str, reader);

            }
        } else
            throw new OperatorException("Expression isn't of string type");
        state.setExeStack(stack);
        state.setSymTable(symTable);
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public String toString() {
        return "Open " + expr.toString();
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typExp = expr.typecheck(typeEnv);
        if (typExp.equals(new StringType()))
            return typeEnv;
        else
            throw new ToyException("Open File: file path not a string");
    }

}
