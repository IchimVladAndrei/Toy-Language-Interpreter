package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements Value {
    int val;

    @Override
    public IType getType() {
        return new IntType();
    }

    public IntValue() {
        this.val = 0;
    }

    public IntValue(int val) {
        this.val = val;
    }

    @Override
    public Value createCopy() {
        return new IntValue(this.val);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IntValue))
            return false;
        IntValue other = (IntValue) obj;
        return this.val == other.val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return Integer.toString(this.val);
    }

}
