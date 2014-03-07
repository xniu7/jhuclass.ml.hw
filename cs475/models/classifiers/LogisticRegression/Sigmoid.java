package cs475.models.classifiers.LogisticRegression;

import cs475.instances.BinaryLabel;
import cs475.models.classifiers.LinkFunction;

import java.io.Serializable;

/**
 * Created by nx on 2/16/14.
 */
public class Sigmoid extends LinkFunction {

    /**
     * sigmoid function
     * @param z a real number
     * @return a number between 0 and 1
     */
    public double transform(double z){
        return 1.0/(1.0+Math.exp(-z));
    }

    /**
     * the derivative of the sigmoid
     * @param z a real number
     * @return the derivative
     */
    public double derivative(double z){
        return transform(z)*(1-transform(z));
    }

    /**
     * sigmoid prediction, compute sigmoid by the number, then return 0 or 1 classification
     * @param z a real number
     * @return 0 or 1 classification
     */
    public BinaryLabel predict(double z){
        double pred = transform(z);
        return new BinaryLabel(pred,0.5);
    }
}
