package cs475.models.predictors;

import cs475.Predictor;
import cs475.ClassificationLabel;
import cs475.Instance;
import cs475.Label;

import java.util.*;

/**
 * Created by nx on 2/11/14.
 */
public class MajorityPredictor extends Predictor {

    private Label _label = null;

    @Override
    public void train(List<Instance> instances){

        // count the frequencies of labels
        HashMap<String,Integer> labelCounts = new HashMap<String, Integer>();
        if (instances==null) return;
        for (Instance instance : instances){
            int count = 1;
            if (instance==null) continue;
            if (labelCounts.containsKey(instance.getLabel().toString())){
                count += labelCounts.get(instance.getLabel().toString());
            }
            labelCounts.put(instance.getLabel().toString(),count);
        }

        // find the labels with the top frequencies
        int freq = 0;
        ArrayList<String> labels = new ArrayList<String>();
        for (Map.Entry<String,Integer> entry : labelCounts.entrySet()){
            if (entry.getValue()==null||entry.getKey()==null) continue;
            if (entry.getValue()>freq){
                labels.clear();
                freq = entry.getValue();
                labels.add(entry.getKey());
            }else if (entry.getValue()==freq){
                labels.add(entry.getKey());
            }
        }

        //randomly choose one from top labels
        if (labels.size()==0) return;
        Random rand = new Random();
        int pos = rand.nextInt(labels.size());
        if (pos<labels.size() && pos>=0)
            this._label = new ClassificationLabel(Integer.parseInt(labels.get(pos)));
    }

    @Override
    public Label predict(Instance instance){
        return this._label;
    }
}
