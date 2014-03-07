package cs475.models.classifiers.LogisticRegression.InformationGain;

import cs475.Instance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by nx on 2/19/14.
 */
public class Mean {
    private HashMap<Integer, Double> _mean = new HashMap<Integer, Double>();

    public Mean(List<Instance> instances){
        if(instances==null) return;
        this.computeMean(instances);
    }

    /**
     * compute mean of all features, for specified feature F1 of instance I1
     * mean(F1) = (F1_I1+F1_I2+...F1_In)/n
     * save all means of features except when mean(Fi) = 0
     * @param instances data has n instances, each instance has m features.
     */
    private void computeMean(List<Instance> instances){
        if(instances==null) return;
        for(Instance instance : instances){
            Iterator<Map.Entry<Integer,Double>> it = instance.getFeatureVector().getKeyValue();
            while(it.hasNext()){
                Map.Entry<Integer,Double> entry = it.next();
                int key = entry.getKey();
                double value = this.getMean(key)+entry.getValue()/instances.size();
                if(value!=0)
                    this._mean.put(key, value);
                else
                    this._mean.remove(key);
            }
        }
    }

    /**
     * get mean of specified key
     * @param key feature id
     * @return mean of specified feature
     */
    public double getMean(Integer key){
        if(this._mean.containsKey(key))
            return this._mean.get(key);
        else
            return 0.0;
    }

    public Iterator<Integer> getKeys(){
        return this._mean.keySet().iterator();
    }
}
