package model.types;

import model.values.IntValue;
import model.values.Value;

public class IntType implements IType {
    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

}
