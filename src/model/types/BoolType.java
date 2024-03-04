package model.types;

import model.values.BooleanValue;
import model.values.Value;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BooleanValue(false);
    }

}
