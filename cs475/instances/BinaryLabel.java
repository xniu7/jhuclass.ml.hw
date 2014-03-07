package cs475.instances;

import cs475.ClassificationLabel;

import java.io.Serializable;

/**
 * Created by nx on 3/2/14.
 */
public class BinaryLabel extends ClassificationLabel implements Serializable {
    /**
     * constructor
     * @param label integer --> (0,1)
     */
    public BinaryLabel(int label, double boundary) {
        super(label>=boundary ? 1 : 0);
    }

    /**
     * constructor
     * @param label double --> (0,1)
     */
    public BinaryLabel(double label, double boundary) {
        super(label>=boundary? 1 : 0);
    }
}
