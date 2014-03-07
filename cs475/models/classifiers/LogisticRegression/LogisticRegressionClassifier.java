package cs475.models.classifiers.LogisticRegression;

import cs475.Instance;
import cs475.Label;
import cs475.FeatureVector;
import cs475.models.classifiers.Classifier;
import cs475.models.classifiers.LogisticRegression.InformationGain.InformationGain;
import cs475.models.classifiers.LogisticRegression.InformationGain.Mean;
import cs475.CommandLineUtilities;

import java.io.Serializable;
import java.util.*;

/**
 * Created by nx on 2/16/14.
 */
public class LogisticRegressionClassifier extends Classifier implements Serializable {

    //20 steps to finish the iteration
    private int _gd_iterations = 20;
    //each step update .01
    private double _gd_eta = .01;
    // <=0 means select all features, otherwise a specific number of features
    private int _num_features_to_select = 0;

    public LogisticRegressionClassifier(){
        super(new Sigmoid());
        if (CommandLineUtilities.hasArg("gd_eta"))
            _gd_eta = CommandLineUtilities.getOptionValueAsFloat("gd_eta");
        if (CommandLineUtilities.hasArg("gd_iterations"))
            _gd_iterations = CommandLineUtilities.getOptionValueAsInt("gd_iterations");
        if (CommandLineUtilities.hasArg("num_features_to_select"))
            _num_features_to_select = CommandLineUtilities.getOptionValueAsInt("num_features_to_select");
    }

    /**
     * compute loss derivative
     * @param instances instances
     * @param w theta
     * @return the derivative of the loss function
     */
    private FeatureVector lossDerivative(List<Instance> instances, FeatureVector w){
        if(instances==null||w==null) return null;
        FeatureVector derivative = new FeatureVector();
        for (Instance instance : instances){
            if (instance==null||instance.getFeatureVector()==null||instance.getLabel()==null) continue;
            double wx = FeatureVector.dotProduct(w,instance.getFeatureVector());
            try{
                double y = instance.getLabel().get_numeric();
                if (y==0 && y==1) continue;
                double scalar = y==1 ? y*this._linkFunction.transform(-wx) : (y-1)*this._linkFunction.transform(wx);
                FeatureVector temp = FeatureVector.scalarProduct(scalar, instance.getFeatureVector());
                FeatureVector.sum(temp,derivative);
            }catch(Exception e){
                System.out.println("Label can not be converted to numeric");
            }
        }
        return derivative;
    }

    /**
     * update w in each small step
     * @param instances instances
     * @param w theta
     */
    private void update(List<Instance> instances, FeatureVector w){
        if(w==null) return;
        FeatureVector derivative = this.lossDerivative(instances, w);
        FeatureVector temp = FeatureVector.scalarProduct(this._gd_eta, derivative);
        FeatureVector.sum(temp, w);
    }

    /**
     * select top features
     * @param instances total instances
     * @param num_features_to_select the number of top features we want
     * @return
     */
    private List<Instance> featureSelect(List<Instance> instances, int num_features_to_select){
        if(instances==null) return null;
        if(num_features_to_select<=0) return instances;
        Mean mean = new Mean(instances);
        InformationGain IG = new InformationGain(instances,mean);
        HashSet<Integer> featureIDs = IG.getTopIG(num_features_to_select);
        for(Instance instance : instances){
            Iterator<Integer> it = instance.getFeatureVector().getKey();
            while(it.hasNext()){
                int key = it.next();
                if(!featureIDs.contains(key))
                    it.remove();
            }
        }
        return instances;
    }

    @Override
    public void train(List<Instance> instances){
        instances = featureSelect(instances, this._num_features_to_select);
        for (int i=0;i<this._gd_iterations;i++){
            update(instances, this._w);
        }
    }

    @Override
    public Label predict(Instance instance){
        double pred = FeatureVector.dotProduct(instance.getFeatureVector(),this._w);
        return this._linkFunction.predict(pred);
    }
}
