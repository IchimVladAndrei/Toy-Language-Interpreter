package model.statements;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.Value;

public class WhileStmt implements IStmt {
    IExpression expr;
    IStmt statement;

    public WhileStmt(IExpression expr, IStmt statement) {
        this.expr = expr;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIStack<IStmt> stack = state.getExeStack();
        MyIHeap<Value> heapTable = state.getHeapTable();
        Value val = expr.eval(symTable, heapTable);
        if (val.getType().equals(new BoolType())) {
            BooleanValue tmp = (BooleanValue) val;
            if (tmp.equals(new BooleanValue(true))) {
                stack.push(this);
                stack.push(statement);
            }

        }

        else
            throw new ToyException("Condition expr is not a booolean");
        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return "WhileStmt(" + expr.toString() + "){" + statement.toString() + "}";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typ = expr.typecheck(typeEnv);
        if (typ.equals(new BoolType())) {
            this.statement.typecheck(typeEnv.copy());
            return typeEnv;
            /// In this case we also use a copy of the typeEnv because we can have the case
            /// while(...)
            /// { int a; }
            /// bool a;
            /// And here for example, if the condition of while is not true, we would still
            /// declare "a" as an int in the typeEnv through the typeChecking even though we
            /// shouldn't
            /// And when we will try to declare "a" as a BOOL it will crash, reason why we
            /// use a copy of the typeEnv when we check the whileBody with the typeChecker

        } else
            throw new ToyException("conditional expression not a boolean");
    }

}
