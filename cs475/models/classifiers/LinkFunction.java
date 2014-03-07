package cs475.models.classifiers;

import cs475.FeatureVector;
import cs475.Label;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by nx on 3/2/14.
 */
public abstract class LinkFunction implements Serializable {
    public abstract double transform(double z);
    public abstract Label predict(double z);

    /**
     * vector computation, each element run linkFunction
     * @param featureVector featureVector
     * @return transformed featureVector
     */
    public FeatureVector vectorCompute(FeatureVector featureVector){
        if (featureVector==null) return null;
        Iterator<Integer> it = featureVector.getKey();
        while(it.hasNext()){
            int key = it.next();
            featureVector.add(key,transform(featureVector.get(key)));
        }
        return featureVector;
    }
}
