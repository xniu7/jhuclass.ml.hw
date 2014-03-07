package cs475.models.classifiers.OnlineLearning;

import cs475.instances.BinaryLabel;
import cs475.Label;
import cs475.models.classifiers.LinkFunction;

/**
 * Created by nx on 3/2/14.
 */
public class MarginPerceptron extends LinkFunction {
    /**
     * sigmoid function
     * @param z a real number
     * @return a number between 0 and 1
     */
    @Override
    public double transform(double z){
        return z;
    }

    public int transformLabel(Label label){
        if(label.get_numeric()>0) return 1;
        else return -1;
    }

    /**
     * sigmoid prediction, compute sigmoid by the number, then return 0 or 1 classification
     * @param z a real number
     * @return 0 or 1 classification
     */
    @Override
    public BinaryLabel predict(double z){
        double pred = transform(z);
        return new BinaryLabel(pred,0);
    }
}
