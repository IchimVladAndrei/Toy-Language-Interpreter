package model.statements;

import exceptions.ADTException;
import exceptions.OperatorException;
import exceptions.StmtException;
import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.values.Value;

public class AssignStmt implements IStmt {
    String id;
    IExpression exp;

    public AssignStmt(String id, IExpression exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws OperatorException, StmtException, ADTException {
        MyIDict<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, heapTable);
            IType typId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new StmtException(
                        "declared type of variable" + id + " and type of the assigned expression do not match");
            return null;
        }
        return null;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typeVar = typeEnv.lookUp(id);
        IType typExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typExp))
            return typeEnv;
        else
            throw new ToyException("Assignment: right hand side and left hand side have different types ");
    }

}
