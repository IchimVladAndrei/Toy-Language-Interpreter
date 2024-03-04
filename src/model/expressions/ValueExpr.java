package model.expressions;

import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.Value;

public class ValueExpr implements IExpression {
    private Value val;

    public ValueExpr(Value val) {
        this.val = val;
    }

    @Override
    public Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable) throws OperatorException {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        return val.getType();
    }

}
