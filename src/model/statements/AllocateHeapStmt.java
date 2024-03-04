package model.statements;

import java.io.IOException;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class AllocateHeapStmt implements IStmt {
    String varName;
    IExpression expr;

    public AllocateHeapStmt(String varName, IExpression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        if (symTable.isDefined(varName)) {
            if (symTable.lookUp(varName).getType() instanceof RefType) {
                Value val = expr.eval(symTable, heapTable);
                Value valHeapTable = expr.eval(symTable, heapTable);
                if (val.getType().equals(valHeapTable.getType())) {
                    int newAddr = heapTable.put(val);
                    symTable.update(varName, new RefValue(newAddr, val.getType()));
                } else
                    throw new ToyException("Value is not of correct type");
            } else
                throw new ToyException("Variable not of RefType");
        } else
            throw new ToyException("Variable not defined");
        state.setExeStack(stack);
        state.setHeapTable(heapTable);
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public String toString() {
        return "new (" + varName + "," + expr.toString() + ")";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typeVar = typeEnv.lookUp(varName);
        IType typeExp = expr.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new ToyException("new stmt:right hand side and left hand side have different types");
    }

}
