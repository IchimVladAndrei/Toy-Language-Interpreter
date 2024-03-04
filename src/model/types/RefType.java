package model.types;

import model.values.RefValue;
import model.values.Value;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType) {
            // RefType another = (RefType) obj;
            // return inner.equals(another.getInner());
            return inner.equals(((RefType) obj).getInner());
        } else
            return false;
    }
}
