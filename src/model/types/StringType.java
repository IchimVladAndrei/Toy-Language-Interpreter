package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements IType {

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

}
