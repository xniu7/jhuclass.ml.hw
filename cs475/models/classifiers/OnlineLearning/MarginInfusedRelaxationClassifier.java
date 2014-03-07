package cs475.models.classifiers.OnlineLearning;

import cs475.Instance;
import cs475.Label;
import cs475.FeatureVector;

import java.util.List;

/**
 * Created by nx on 3/3/14.
 */
public class MarginInfusedRelaxationClassifier extends OnlineLearningClassifier {
    public MarginInfusedRelaxationClassifier(){
        super(new MarginPerceptron());
    }

    protected void update(Instance instance, FeatureVector w, double learning_rate){
        int y = ((MarginPerceptron)this._linkFunction).transformLabel(instance.getLabel());
        FeatureVector x = instance.getFeatureVector();
        FeatureVector res = FeatureVector.scalarProduct(learning_rate * y, x);
        FeatureVector.sum(res,w);
    }

    private double getLearningRate(Instance instance){
        MarginPerceptron marginPerceptron = (MarginPerceptron)this._linkFunction;
        int y = marginPerceptron.transformLabel(instance.getLabel());
        double wx = FeatureVector.dotProduct(instance.getFeatureVector(),this._w);
        double xx = FeatureVector.dotProduct(instance.getFeatureVector(), instance.getFeatureVector());
        double dynamic_rate = (1-y*wx)/xx;
        return dynamic_rate;
    }

    @Override
    public void train(List<Instance> instances){
        for(int i=0;i<this._online_training_iterations;i++){
            for(Instance instance:instances){
                double dynamic_rate = getLearningRate(instance);
                double y_pred = FeatureVector.dotProduct(instance.getFeatureVector(),this._w);
                y_pred=this._linkFunction.transform(y_pred);
                int y = ((MarginPerceptron)this._linkFunction).transformLabel(instance.getLabel());
                if(y*y_pred<1){
                    update(instance,this._w, dynamic_rate);
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
