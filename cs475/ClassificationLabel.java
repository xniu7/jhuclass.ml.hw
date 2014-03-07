package cs475;

import cs475.Label;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {

	public ClassificationLabel(int label) {
		// TODO Auto-generated constructor stub
        this._label = String.valueOf(label);
        this._numeric = label;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
	    return this._label;
	}
}
