package cs475.models.classifiers.OnlineLearning;

import cs475.FeatureVector;
import cs475.Instance;
import cs475.Label;

import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public class DualPerceptronClassifier extends OnlineLearningClassifier {
    private FeatureVector _alpha = new FeatureVector();
    private FeatureVector _y = new FeatureVector();
    private List<Instance> _instances;

    public DualPerceptronClassifier(){
        super(new DualPerceptron());
    }

    private void setParameters(List<Instance> instances){
        for(int i=0;i<instances.size();i++){
            this._y.add(i,instances.get(i).getLabel().get_numeric());
        }
        this._instances = instances;
        DualPerceptron dualPerceptron = ((DualPerceptron)this._linkFunction);
        dualPerceptron.initKernel(instances);
    }

    @Override
    public void train(List<Instance> instances){
        if(instances==null) return;
        setParameters(instances);
        DualPerceptron dualPerceptron = ((DualPerceptron)this._linkFunction);

        for(int iter=0;iter<this._online_training_iterations;iter++){
            //for every new instance, in this case, it is every instance we have.
            for(int new_instance_id=0;new_instance_id<instances.size();new_instance_id++){
                double y_pred=0;
                for(int i=0;i<instances.size();i++){
                    double y = dualPerceptron.transformLabel(instances.get(i).getLabel());
                    double ker = dualPerceptron.getKernel(i, new_instance_id);
                    y_pred += this._alpha.get(i)*y*ker;
                }
                int y = dualPerceptron.transformLabel(instances.get(new_instance_id).getLabel());
                if(y*y_pred<1){
                    this._alpha.add(new_instance_id,this._alpha.get(new_instance_id)+1);
                }
            }
        }

        dualPerceptron.releaseKernel();
    }

    @Override
    public Label predict(Instance instance){
        double y_pred=0;
        DualPerceptron dualPerceptron = ((DualPerceptron)this._linkFunction);
        FeatureVector fv = instance.getFeatureVector();
        for(int i=0;i<this._instances.size();i++){
            double y = dualPerceptron.transform(this._y.get(i));
            FeatureVector fv2 = _instances.get(i).getFeatureVector();
            double ker = dualPerceptron.getKernel(fv,fv2);
            y_pred += this._alpha.get(i)*y*ker;
        }
        y_pred = dualPerceptron.transform(y_pred);
        return this._linkFunction.predict(y_pred);
    }
}
