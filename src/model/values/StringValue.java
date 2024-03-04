package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements Value {
    String txt;

    public StringValue(String txt) {
        this.txt = txt;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public Value createCopy() {
        return new StringValue(txt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof StringValue))
            return false;
        StringValue other = (StringValue) obj;
        return this.txt == other.txt;
    }

    @Override
    public String toString() {
        return txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

}
