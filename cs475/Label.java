package cs475;

import java.io.Serializable;

public abstract class Label implements Serializable {
    protected String _label;
    protected double _numeric;

	public abstract String toString();

    public int hashCode(){
        return this._label.hashCode();
    }

    public boolean equals(Object obj){
        double obj_numeric = Double.valueOf(obj.toString());
        if(this._label.equals(obj.toString())||this._numeric==obj_numeric)
            return true;
        else
            return false;
    }

    public double get_numeric(){
        return this._numeric;
    }
}
