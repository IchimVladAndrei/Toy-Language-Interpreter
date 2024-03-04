package model.statements;

import java.io.IOException;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;

public class CondStmt implements IStmt {
    IExpression expr1, expr2, expr3;
    String id;

    public CondStmt(IExpression expr1, IExpression expr2, IExpression expr3, String id) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expr3 = expr3;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s=%s?%s:%s", id, expr1, expr2, expr3);
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        IStmt tmp = new IfStatement(new AssignStmt(id, expr2), new AssignStmt(id, expr3), expr1);
        state.getExeStack().push(tmp);
        return null;
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType expr1Type, expr2Type, expr3Type, vType;
        expr1Type = expr1.typecheck(typeEnv);
        expr2Type = expr2.typecheck(typeEnv);
        expr3Type = expr2.typecheck(typeEnv);
        vType = typeEnv.lookUp(id);
        if (expr1Type.equals(new BoolType())) {
            if (vType.equals(expr2Type) && vType.equals(expr3Type)) {
                return typeEnv;
            } else
                throw new ToyException("expr2,expr3 and variable of diferent types");
        } else
            throw new ToyException("expr1 is not of type bool");
    }
}
