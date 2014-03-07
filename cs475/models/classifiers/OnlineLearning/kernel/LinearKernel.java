package cs475.models.classifiers.OnlineLearning.kernel;

import cs475.Instance;
import cs475.FeatureVector;

import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public class LinearKernel extends Kernel {
    public LinearKernel(List<Instance> instances){
        init(instances);
    }
    public double function(FeatureVector X, FeatureVector Z){
        double k = FeatureVector.dotProduct(X, Z);
        return k;
    }
}
