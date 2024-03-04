package model.expressions;

import exceptions.ADTException;
import exceptions.OperatorException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpr implements IExpression {

    IExpression firstOp, secondOp;
    String operator;

    public RelationalExpr(IExpression firstOp, IExpression secondOp, String operator) {
        this.firstOp = firstOp;
        this.secondOp = secondOp;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDict<String, Value> tbl, MyIHeap<Value> heapTable) throws OperatorException, ADTException {

        Value value1 = firstOp.eval(tbl, heapTable);
        if (value1.getType().equals(new IntType())) {
            Value value2 = secondOp.eval(tbl, heapTable);
            if (value2.getType().equals(new IntType())) {
                IntValue tmp1 = (IntValue) value1;
                IntValue tmp2 = (IntValue) value2;
                int intTmp1 = tmp1.getVal();
                int intTmp2 = tmp2.getVal();
                return switch (operator) {
                    case "<" -> new BooleanValue(intTmp1 < intTmp2);
                    case "<=" -> new BooleanValue(intTmp1 <= intTmp2);
                    case "==" -> new BooleanValue(intTmp1 == intTmp2);
                    case "!=" -> new BooleanValue(intTmp1 != intTmp2);
                    case ">" -> new BooleanValue(intTmp1 > intTmp2);
                    case ">=" -> new BooleanValue(intTmp1 >= intTmp2);
                    default -> throw new OperatorException("invalid operator");
                };
            } else
                throw new OperatorException("second operand not an INT");
        } else
            throw new OperatorException("first operand not an INT");
    }

    @Override
    public String toString() {
        return firstOp.toString() + operator + secondOp.toString();
    }

    @Override
    public IType typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typ1, typ2;
        typ1 = firstOp.typecheck(typeEnv);
        typ2 = secondOp.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType()))
                return new BoolType();
            else
                throw new ToyException("second operant is not an integer");
        } else
            throw new ToyException("first operand is not an integer");
    }
}
