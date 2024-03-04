package model.expressions;

import exceptions.ADTException;
import exceptions.DivisionByZeroException;
import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpr implements IExpression {
    private IExpression firstOp, secondOp;
    private char operand;

    public ArithmeticExpr(IExpression firstOp, IExpression secondOp, char operand) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
        this.operand = operand;
    }

    @Override
    public Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable)
            throws OperatorException, DivisionByZeroException, ADTException {
        Value v1, v2;

        v1 = firstOp.eval(tbl, heapTable);
        if (v1.getType().equals(new IntType())) {
            v2 = secondOp.eval(tbl, heapTable);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int a1 = i1.getVal();
                int a2 = i2.getVal();
                if (operand == '+')
                    return new IntValue(a1 + a2);
                if (operand == '-')
                    return new IntValue(a1 - a2);
                if (operand == '*')
                    return new IntValue(a1 * a2);
                if (operand == '/')
                    if (a2 == 0)
                        throw new DivisionByZeroException("division by 0");
                    else
                        return new IntValue(a1 / a2);
            } else
                throw new OperatorException("second operand not an integer");

        } else
            throw new OperatorException("first operand not an integer");
        throw new OperatorException("no operation found");
    }

    @Override
    public String toString() {
        return firstOp.toString() + operand + secondOp.toString();
    }

    @Override
    public IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typ1, typ2;
        typ1 = firstOp.typecheck(typeEnv);
        typ2 = secondOp.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType()))
                return new IntType();
            else
                throw new ToyException("second operand is not an integer");
        } else
            throw new ToyException("first operand is not an integer");
    }
}
