package model.statements;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.PrgState;
import model.types.IType;

public class EmptyStmt implements IStmt {

    @Override
    public String toString() {
        return "Empty statement!";
    }

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        return typeEnv;
    }

}
