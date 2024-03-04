package model.statements;

import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.values.Value;
import exceptions.ToyException;

public class PrintStmt implements IStmt {
    private IExpression expr;

    public PrintStmt(IExpression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException {
        MyIList<Value> output = state.getOut();
        MyIDict<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();

        output.add(expr.eval(symTbl, heapTable));
        return null;

    }

    @Override
    public String toString() {
        return "print(" + expr.toString() + ")";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        expr.typecheck(typeEnv);
        return typeEnv;
    }

}
