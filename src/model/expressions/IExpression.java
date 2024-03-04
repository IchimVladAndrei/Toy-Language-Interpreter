package model.expressions;

import exceptions.ADTException;
import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.Value;

public interface IExpression {
    Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable) throws OperatorException, ADTException;

    IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException;
}
