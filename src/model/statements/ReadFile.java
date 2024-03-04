package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class ReadFile implements IStmt {
    IExpression expr;
    String varName;

    public ReadFile(IExpression expr, String varName) {
        this.expr = expr;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        // Value val = expr.eval(symTable);
        if (symTable.isDefined(varName)) {
            Value val = symTable.lookUp(varName);
            if (val.getType().equals(new IntType())) {
                Value val1 = expr.eval(symTable, heapTable);
                if (val1.getType().equals(new StringType())) {
                    StringValue str = (StringValue) val1;

                    BufferedReader reader = fileTable.lookUp(str);
                    String line = reader.readLine();
                    IntValue tmp;
                    if (line == null)
                        tmp = new IntValue();
                    else
                        tmp = new IntValue(Integer.parseInt(line));
                    symTable.update(varName, tmp);

                } else
                    throw new OperatorException("can't read, expr not type STRING");
            } else
                throw new OperatorException("variable type not an INT");
        } else
            throw new OperatorException("variable name isn't defiened");
        state.setFileTable(fileTable);
        state.setSymTable(symTable);
        state.setExeStack(stack);
        return null;

    }

    @Override
    public String toString() {
        return "Read (" + expr.toString() + "in var" + varName + " ";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typeVar = typeEnv.lookUp(varName);
        IType typeExp = expr.typecheck(typeEnv);
        if (typeVar.equals(new IntType())) {
            if (typeExp.equals(new StringType()))
                return typeEnv;
            else
                throw new ToyException("Read file: file path not a string type");

        } else
            throw new ToyException("Read file: the variable is not an integer type");
    }

}
