package model.values;

import model.types.BoolType;
import model.types.IType;

public class BooleanValue implements Value {
    Boolean val;
    public BooleanValue(Boolean val){
        this.val=val;
    }
    public BooleanValue(){
        this.val=false;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public Value createCopy() {
        return new BooleanValue(this.val);
    }
    @Override
    public String toString() {
        return Boolean.toString(this.val);
    }
    public Boolean getVal() {
        return val;
    }
    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if(!(obj instanceof BooleanValue)) return false;
        BooleanValue other=(BooleanValue)obj;
        return this.val==other.val;
    }
    
}
