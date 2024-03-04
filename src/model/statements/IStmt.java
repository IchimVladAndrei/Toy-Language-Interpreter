package model.statements;

import java.io.IOException;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.PrgState;
import model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws ToyException, IOException;

    String toString();

    MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException;

}
