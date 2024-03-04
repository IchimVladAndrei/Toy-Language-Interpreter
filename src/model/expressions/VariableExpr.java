package model.expressions;

import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.Value;

public class VariableExpr implements IExpression {
    private String id;

    public VariableExpr(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable) throws OperatorException {
        if (tbl.lookUp(id) == null) {
            throw new OperatorException(id + " not defined as a variable!");

        }
        return tbl.lookUp(id);

    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        return typeEnv.lookUp(id);
    }

}
