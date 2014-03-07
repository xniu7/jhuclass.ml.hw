package cs475.models.classifiers.LogisticRegression.InformationGain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nx on 2/19/14.
 */
public class Probabilities {
    //save count of each event
    private HashMap<Integer,Double> _count = new HashMap<Integer, Double>();
    //save the probability of each event
    private HashMap<Integer,Double> _prob = new HashMap<Integer, Double>();
    //if the prob is out of date (another event happen), then compute prob again.
    private boolean _prob_is_uptodate = false;
    //the total count of all events
    private double _count_total=0;

    /**
     * update the count of specified event
     * @param event event
     * @param count count [0,1...]
     */
    public void update(int event, double count){
        //update the count of specified event
        if(!this._count.containsKey(event))
            this._count.put(event,count);
        else
            this._count.put(event,this._count.get(event)+count);
        //update the count of all event
        this._count_total+=count;
        //after count updated, the probability is out of date
        this._prob_is_uptodate = false;
    }

    /**
     * compute probability given the count of all up-to-date events
     */
    protected void checkProb(){
        if(this._prob_is_uptodate) return;
        for(Map.Entry<Integer,Double> entry : this._count.entrySet()){
            if(entry==null) continue;
            this._prob.put(entry.getKey(), entry.getValue() / this._count_total);
        }
        this._prob_is_uptodate = true;
    }

    /**
     * get the probability of specified event
     * @param event event
     * @return probability
     */
    public double getProb(int event) {
        checkProb();
        if(!this._prob.containsKey(event))
            return 0.0; //if this event is not exist, it means the probability is zero.
        else
            return this._prob.get(event);
    }

    /**
     * get the probabilities
     * @return probabilities collection iterator
     */
    public Iterator<Double> getProbs(){
        checkProb();
        return this._prob.values().iterator();
    }

    /**
     * get the events
     * @return events set iterator
     */
    public Iterator<Integer> getEvents(){
        checkProb();
        return this._prob.keySet().iterator();
    }

    /**
     * get the <event,probability> entries
     * @return <event,probability> entries iterator
     */
    public Iterator<Map.Entry<Integer,Double>> getEventProbs(){
        checkProb();
        return this._prob.entrySet().iterator();
    }

    public double get_count_total() {
        return _count_total;
    }
}
