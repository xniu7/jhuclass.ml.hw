package cs475.models.predictors;

import cs475.Predictor;
import cs475.ClassificationLabel;
import cs475.Instance;
import cs475.Label;

import java.util.*;

/**
 * Created by nx on 2/11/14.
 */
public class EvenOddPredictor extends Predictor {

    @Override
    public void train(List<Instance> instances){
        //nothing to do here
    }

    @Override
    public Label predict(Instance instance){
        if (instance==null||instance.getFeatureVector()==null)
            return null;

        double even_sum=0.0, odd_sum = 0.0;
        Iterator<Integer> it = instance.getFeatureVector().getKey();

        //sum even features and odd features
        while(it.hasNext()){
            int key = it.next();
            if(key%2==0)
                even_sum+=instance.getFeatureVector().get(key);
            else
                odd_sum +=instance.getFeatureVector().get(key);
        }

        // if even features larger, predict 1; else, predict 0
        if (even_sum>=odd_sum)
            return new ClassificationLabel(1);
        else
            return new ClassificationLabel(0);
    }
}
