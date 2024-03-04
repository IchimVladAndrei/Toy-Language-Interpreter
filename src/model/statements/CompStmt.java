package model.statements;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.types.IType;

public class CompStmt implements IStmt {
    IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public String toString() {
        return first.toString() + ";" + second.toString();
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

}
