package model.statements;

import java.io.IOException;

import exceptions.ToyException;
import model.adt.MyIDict;
import model.adt.MyIHeap;
import model.adt.PrgState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class WriteHeapStmt implements IStmt {
    String varName;
    IExpression expr;

    public WriteHeapStmt(String varName, IExpression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyException, IOException {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();
        if (symTable.isDefined(varName)) {
            if (symTable.lookUp(varName).getType() instanceof RefType) {
                int addr = ((RefValue) symTable.lookUp(varName)).getAddress();
                if (heapTable.isDefined(addr)) {
                    Value valExpr = expr.eval(symTable, heapTable);

                    if (valExpr.getType().equals(heapTable.getContent().get(addr).getType())) {
                        heapTable.update(addr, valExpr);// valrefinner
                    } else
                        throw new ToyException("Expression value type not same with location_type");
                } else
                    throw new ToyException("The address is not a key in heapTable");
            } else
                throw new ToyException("Variable is not of RefType");
        } else
            throw new ToyException("Variable is not defined in the symTable");
        state.setHeapTable(heapTable);
        return null;

    }

    @Override
    public String toString() {
        return "WriteHeap (" + varName + "," + expr.toString() + ")";
    }

    @Override
    public MyIDict<String, IType> typecheck(MyIDict<String, IType> typeEnv) throws ToyException {
        IType typeVar = typeEnv.lookUp(varName);
        IType typeExp = expr.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new ToyException("Heap write: Expression cannot be evaluated correctly");
    }

}
