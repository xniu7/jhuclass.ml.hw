package cs475.models.classifiers.OnlineLearning;

import cs475.FeatureVector;
import cs475.Instance;
import cs475.Label;
import cs475.CommandLineUtilities;

import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public class MarginPerceptronClassifier extends OnlineLearningClassifier {
    //the learning rate of each step
    double _online_learning_rate = 1.0;


    public MarginPerceptronClassifier(){
        super(new MarginPerceptron());
        if (CommandLineUtilities.hasArg("online_learning_rate"))
            _online_learning_rate = CommandLineUtilities.getOptionValueAsFloat("online_learning_rate");

    }

    protected void update(Instance instance, FeatureVector w){
        int y = ((MarginPerceptron)this._linkFunction).transformLabel(instance.getLabel());
        FeatureVector x = instance.getFeatureVector();
        FeatureVector res = FeatureVector.scalarProduct(this._online_learning_rate * y, x);
        FeatureVector.sum(res,w);
    }

    @Override
    public void train(List<Instance> instances){
        for(int i=0;i<this._online_training_iterations;i++){
            for(Instance instance:instances){
                double y_pred = FeatureVector.dotProduct(instance.getFeatureVector(),this._w);
                y_pred=this._linkFunction.transform(y_pred);
                int y = ((MarginPerceptron)this._linkFunction).transformLabel(instance.getLabel());
                if(y*y_pred<1){
                    update(instance,this._w);
                }
            }
        }
    }

    @Override
    public Label predict(Instance instance){
        double pred = FeatureVector.dotProduct(instance.getFeatureVector(),this._w);
        return this._linkFunction.predict(pred);
    }
}
