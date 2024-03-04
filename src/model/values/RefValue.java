package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements Value {
    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public Value createCopy() {
        return new RefValue(address, locationType);
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType.toString() + ")";
    }
}
