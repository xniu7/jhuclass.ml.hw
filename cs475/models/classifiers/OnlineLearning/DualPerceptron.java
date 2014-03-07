package cs475.models.classifiers.OnlineLearning;
import cs475.CommandLineUtilities;
import cs475.FeatureVector;
import cs475.Instance;
import cs475.Label;
import cs475.instances.BinaryLabel;
import cs475.models.classifiers.LinkFunction;
import cs475.models.classifiers.OnlineLearning.kernel.Kernel;
import cs475.models.classifiers.OnlineLearning.kernel.LinearKernel;
import cs475.models.classifiers.OnlineLearning.kernel.PolynomialKernel;

import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public class DualPerceptron extends LinkFunction {
    private String  _algorithm = "";
    private Kernel _kernel;

    public DualPerceptron(){
        this._algorithm=CommandLineUtilities.getOptionValue("algorithm");
    }

    public void initKernel(List<Instance> instances){
        if(this._algorithm.equals("perceptron_linear_kernel"))
            this._kernel = new LinearKernel(instances);
        else if(this._algorithm.equals("perceptron_polynomial_kernel"))
            this._kernel = new PolynomialKernel(instances);
    }

    public void releaseKernel(){
        this._kernel.release();
    }

    public double getKernel(int row_id, int col_id){
        return this._kernel.get(row_id,col_id);
    }

    public double getKernel(FeatureVector fv1, FeatureVector fv2){
        return this._kernel.function(fv1,fv2);
    }

    @Override
    public double transform(double z){
        if(z>0) return 1;
        else return -1;
    }

    public int transformLabel(Label label){
        if(label.get_numeric()>0) return 1;
        else return -1;
    }

    @Override
    public BinaryLabel predict(double z){
        double pred = transform(z);
        return new BinaryLabel(pred,0);
    }
}
