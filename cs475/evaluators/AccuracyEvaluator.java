package cs475.evaluators;

import cs475.Evaluator;
import cs475.Instance;
import cs475.Predictor;

import java.util.List;

public class AccuracyEvaluator extends Evaluator {

    @Override  //count correct predictions, then divide by total instances.
    public double evaluate(List<Instance> instances, Predictor predictor){
        if (predictor==null||instances==null||instances.size()==0) return 0.0;

        int correct =0;
        for (Instance instance : instances){
            if (predictor.predict(instance)==null||instance.getLabel()==null) continue;
            if (instance.getLabel().toString().equalsIgnoreCase(predictor.predict(instance).toString()))
                correct++;
        }
        return 1.0*correct/instances.size();
    }
}