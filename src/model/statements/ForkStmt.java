package model.statements;

import java.io.IOException;
import java.util.Map;

import exceptions.ToyException;
import model.adt.MyDict;
import model.adt.MyIDict;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.adt.PrgState;
import model.types.IType;
import model.values.Value;

public class ForkStmt implements IStmt {
    IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        MyIDict<String, Value> newSymTable = new MyDict<>();

        for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().createCopy());
        }
        MyIStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);

        PrgState newPrg = new PrgState(newStack, newSymTable, state.getOut(), state.getFileTable(),
                state.getHeapTable());
        newPrg.setId();
        return newPrg;
    }

    @Override
    public String toString() {
        return "Fork(" + statement.toString() + ")";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        this.statement.typecheck(typeEnv.copy());
        return typeEnv;
    }

}
