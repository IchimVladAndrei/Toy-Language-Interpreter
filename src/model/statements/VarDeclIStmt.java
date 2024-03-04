package model.statements;

import exceptions.ToyException;
import model.values.Value;
import model.adt.MyIDict;
import model.adt.PrgState;
import model.types.IType;

public class VarDeclIStmt implements IStmt {
    String name;
    IType typ;

    public VarDeclIStmt(String name, IType typ) {
        this.name = name;
        this.typ = typ;

    }

    @Override
    public PrgState execute(PrgState state) throws ToyException {
        MyIDict<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
        } else

        {
            Value defValue = typ.defaultValue();
            symTable.put(name, defValue);
        }
        state.setSymTable(symTable);

        return null;
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name;
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        typeEnv.put(name, typ);
        return typeEnv;
    }
}
