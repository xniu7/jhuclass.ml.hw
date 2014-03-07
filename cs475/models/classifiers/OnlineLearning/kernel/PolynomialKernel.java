package cs475.models.classifiers.OnlineLearning.kernel;

import cs475.CommandLineUtilities;
import cs475.Instance;
import cs475.FeatureVector;

import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public class PolynomialKernel extends Kernel {
    protected double _polynomial_kernel_exponent=2;

    public PolynomialKernel(List<Instance> instances){
        if (CommandLineUtilities.hasArg("polynomial_kernel_exponent"))
            _polynomial_kernel_exponent = CommandLineUtilities.getOptionValueAsInt("polynomial_kernel_exponent");
        init(instances);
    }
    public double function(FeatureVector X, FeatureVector Z){
        double temp = FeatureVector.dotProduct(X, Z);
        double k = Math.pow(1.0+temp,this._polynomial_kernel_exponent);
        return k;
    }
}
