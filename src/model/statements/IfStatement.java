package model.statements;

import model.adt.MyIDict;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.Value;
import exceptions.ADTException;
import exceptions.OperatorException;
import exceptions.StmtException;
import exceptions.ToyException;

public class IfStatement implements IStmt {
    IStmt thenS, elseS;
    IExpression exp;

    public IfStatement(IStmt thenS, IStmt elseS, IExpression exp) {
        this.thenS = thenS;
        this.elseS = elseS;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, OperatorException, ADTException {

        MyIStack<IStmt> stack = state.getExeStack();
        Value cond = exp.eval(state.getSymTable(), state.getHeapTable());
        if (cond.equals(new BooleanValue(true))) {
            stack.push(thenS);

        } else {
            stack.push(elseS);
        }
        return null;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        } else
            throw new ToyException("the condition of IF is not the type bool");
    }

}
