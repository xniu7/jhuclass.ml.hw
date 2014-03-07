package cs475;

import java.io.Serializable;

public class RegressionLabel extends Label implements Serializable {

	public RegressionLabel(double label) {
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
