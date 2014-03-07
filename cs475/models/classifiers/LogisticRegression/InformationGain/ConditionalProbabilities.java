package cs475.models.classifiers.LogisticRegression.InformationGain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nx on 2/19/14.
 */
public class ConditionalProbabilities{
    //save the probability of each event
    private HashMap<Integer, Probabilities> _prob = new HashMap<Integer, Probabilities>();
    //if the prob is out of date (another event happen), then compute prob again.
    boolean _prob_is_uptodate = false;
    //the total count of all events
    double _count_total=0;

    /**
     * update the count of specified event under specified condition
     * @param event event
     * @param condition under that condition
     * @param count count [0,1...]
     */
    public void update(int event, int condition, double count){
        //update the count of specified event under specified condition
        if(!this._prob.containsKey(condition)){
            Probabilities prob = new Probabilities();
            prob.update(event, count);
            this._prob.put(condition,prob);
        }
        else
            this._prob.get(condition).update(event,count);

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
        for(Map.Entry<Integer, Probabilities> entry : this._prob.entrySet()){
            if(entry==null) continue;
            entry.getValue().checkProb();
        }
        this._prob_is_uptodate = true;
    }

    /**
     * get the probability of specified event under specified condition
     * @param event event
     * @param condition condition
     * @return probability
     */
    public double getProb(int event, int condition) {
        checkProb();
        if(!this._prob.containsKey(condition))
            return 0.0;
        else
            return this._prob.get(condition).getProb(event);
    }

    /**
     * get the probability of specified condition
     * @param condition condition
     * @return probability
     */
    public Probabilities getProbsByCondition(int condition) {
        checkProb();
        return this._prob.get(condition);
    }

    /**
     * get the probabilities under all conditions
     * @return probabilities collection iterator
     */
    public Iterator<Probabilities> getProbs(){
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
    public Iterator<Map.Entry<Integer,Probabilities>> getEventProbs(){
        checkProb();
        return this._prob.entrySet().iterator();
    }

    public double get_count_total() {
        return _count_total;
    }
}
