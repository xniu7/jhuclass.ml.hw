package cs475.models.classifiers.LogisticRegression.InformationGain;

import cs475.Instance;

import java.util.*;

/**
 * Created by nx on 2/17/14.
 */
public class InformationGain {
    private HashMap<Integer,Double> _IGs = new HashMap<Integer, Double>();
    private HashMap<Integer, Probabilities> _pXs = new HashMap<Integer, Probabilities>();
    private Probabilities _pY = new Probabilities();
    private HashMap<Integer, ConditionalProbabilities> _pYByXs = new HashMap<Integer, ConditionalProbabilities>();

    public InformationGain(List<Instance> instances, Mean mean){
        if(instances==null||mean==null) return;
        createIG(instances,mean);
    }

    private Probabilities getProb(int key, HashMap<Integer,Probabilities> pXs){
        Probabilities prob;
        if(!pXs.containsKey(key)){
            prob = new Probabilities();
            pXs.put(key,prob);
        } else
            prob = pXs.get(key);
        return prob;
    }

    private ConditionalProbabilities getConditionalProb(int key, HashMap<Integer,ConditionalProbabilities> pYByXs){
        ConditionalProbabilities prob;
        if(!pYByXs.containsKey(key)){
            prob = new ConditionalProbabilities();
            pYByXs.put(key,prob);
        } else
            prob = pYByXs.get(key);
        return prob;
    }

    public HashSet<Integer> getTopIG(int topNum){
        return quickSort(topNum);
    }

    private HashSet<Integer> quickSort(int topNum){
        List<Map.Entry<Integer,Double>> featureByIG = new ArrayList<Map.Entry<Integer,Double>>(this._IGs.entrySet());
        Collections.sort(featureByIG, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (o1.getValue() > o2.getValue() )
                    return -1;
                else if (o1.getValue() < o2.getValue() )
                    return 1;
                else
                    return 0;
            }
        });
        HashSet<Integer> featureIDs = new HashSet<Integer>();
        for(int i=0;i<topNum;i++){
            if(i >= featureByIG.size()) break;
            int featureID = featureByIG.get(i).getKey();
            featureIDs.add(featureID);
        }
        return featureIDs;
    }

    private void createIG(List<Instance> instances, Mean mean){
        if(instances==null||mean==null) return;
        for(Instance instance : instances){
            if(instance.getLabel().toString().equalsIgnoreCase("1")){
                this._pY.update(1,1);
                Iterator<Integer> it = mean.getKeys();
                while(it.hasNext()){
                    int key = it.next();
                    double value = instance.getFeatureVector().get(key);
                    Probabilities pX = getProb(key, this._pXs);
                    ConditionalProbabilities pYByX = getConditionalProb(key, this._pYByXs);{
                        if(value>=mean.getMean(key)) {
                            pX.update(1,1);
                            pYByX.update(1, 1, 1);
                        }else{
                            pX.update(0,1);
                            pYByX.update(1,0,1);
                        }
                    }
                }
            }else {
                this._pY.update(0,1);
                Iterator<Integer> it = mean.getKeys();
                while(it.hasNext()){
                    int key = it.next();
                    double value = instance.getFeatureVector().get(key);
                    Probabilities pX = getProb(key, this._pXs);
                    ConditionalProbabilities pYByX = getConditionalProb(key, this._pYByXs);{
                        if(value>=mean.getMean(key)) {
                            pX.update(1,1);
                            pYByX.update(0,1,1);
                        }else{
                            pX.update(0,1);
                            pYByX.update(0, 0, 1);
                        }
                    }
                }
            }
        }
        Iterator<Integer> it = this._pXs.keySet().iterator();
        while(it.hasNext()){
            int key = it.next();
            this._IGs.put(key, InformationGain.gain(this._pXs.get(key), this._pY, this._pYByXs.get(key)));
        }
    }

    /**
     * Compute the entropy of H(Y)
     * @param pY possibility of y1,y2...
     * @return H(Y)
     */
    public static double entropy(Probabilities pY){
        double h = 0.0;
        double py = 0.0;
        if(pY==null) return 0.0;
        Iterator<Double> it = pY.getProbs();
        while(it.hasNext()){
            py = it.next();
            if(py>0)
                h += -py*Math.log(py);
        }
        return h;
    }

    /**
     * Compute the conditional entropy of H(Y|X)
     * @param pX possibility of x1,x2...
     * @param pYByX possibility of yi under the xi condition.
     * @return
     */
    public static double conditionalEntropy(Probabilities pX, ConditionalProbabilities pYByX){
        double h = 0.0;
        double px = 0.0;
        if(pX==null||pYByX==null) return 0.0;
        Iterator<Map.Entry<Integer,Probabilities>> it = pYByX.getEventProbs();
        while(it.hasNext()){
            Map.Entry<Integer,Probabilities> entry =it.next();
            int condition = entry.getKey();
            px = pX.getProb(condition);
            Probabilities pyByX = pYByX.getProbsByCondition(condition);
            h += px*entropy(pyByX);
        }
        return h;
    }

    /**
     * Compute the information gain of IG(Y|X)
     * @param pX possibility of x1,x2...
     * @param pYByX possibility of yi under the xi condition.
     * @return
     */
    public static double gain(Probabilities pX, Probabilities pY, ConditionalProbabilities pYByX){
        double ig = entropy(pY)-conditionalEntropy(pX,pYByX);
        return ig;
    }
}
