package model.values;

import model.types.IType;

public interface Value {
    IType getType();
    Value createCopy();
}
