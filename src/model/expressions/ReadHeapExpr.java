package model.expressions;

import exceptions.ADTException;
import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExpr implements IExpression {
    IExpression expr;

    public ReadHeapExpr(IExpression expr) {
        this.expr = expr;
    }

    @Override
    public Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable) throws OperatorException, ADTException {
        Value val = expr.eval(tbl, heapTable);
        if (val.getType() instanceof RefType) {
            RefValue valRefType = (RefValue) val;
            int addr = valRefType.getAddress();
            if (heapTable.isDefined(addr)) {
                return heapTable.lookUp(addr);

            } else
                throw new OperatorException("Address is not within the heap table");
        } else
            throw new OperatorException("Not a RefType");

    }

    @Override
    public String toString() {
        return "ReadHeap(" + expr.toString() + ")";
    }

    @Override
    public IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typ1 = expr.typecheck(typeEnv);
        if (typ1 instanceof RefType) {
            RefType rType = (RefType) typ1;
            return rType.getInner();
        } else
            throw new ToyException("expression not of reference type");
    }

}
