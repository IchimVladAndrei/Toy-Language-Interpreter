package model.statements;

import java.io.BufferedReader;
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

public class CloseRFile implements IStmt {
    IExpression expr;

    public CloseRFile(IExpression expr) {
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
            if (fileTable.isDefined(str)) {
                BufferedReader tmp = fileTable.lookUp(str);
                tmp.close();
                fileTable.remove(str);

            } else
                throw new FileIOException("tried to close a file that doesn't exist");

        } else
            throw new OperatorException("file name is not a string");

        state.setFileTable(fileTable);
        state.setSymTable(symTable);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return "Close " + expr.toString();
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typVar = expr.typecheck(typeEnv);
        if (typVar.equals(new StringType()))
            return typeEnv;
        else
            throw new ToyException("Close file: file path not a string");
    }

}
