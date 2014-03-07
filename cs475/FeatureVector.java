package cs475;

import cs475.matrix.FeatureMatrix;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeatureVector implements Serializable {

    private HashMap<Integer,Double> _vector = new HashMap<Integer, Double>();

	public void add(int index, double value) {
		// TODO Auto-generated method stub
        this._vector.put(index,value);
	}
	
	public double get(int index) {
		// TODO Auto-generated method stub
        if (this._vector.containsKey(index))
            return this._vector.get(index);
		return 0;
	}

    public void remove(int index){
        if  (this._vector.containsKey(index))
            this._vector.remove(index);
    }

    public int size() {
        // TODO Auto-generated method stub
        if (this._vector.size()>0)
            return this._vector.size();
        return 0;
    }

    public Iterator<Integer> getKey(){
        return this._vector.keySet().iterator();
    }

    public Iterator<Map.Entry<Integer,Double>> getKeyValue(){
        return this._vector.entrySet().iterator();
    }

    /**
     * featureVector multiply a number
     * @param scalar scalar
     * @param fv feature vector
     * @return scaled feature vector
     */
    public static FeatureVector scalarProduct(double scalar, FeatureVector fv){
        if (fv==null) return null;
        Iterator<Integer> it = fv.getKey();
        FeatureVector result = new FeatureVector();
        while(it.hasNext()){
            int key = it.next();
            result.add(key,scalar*fv.get(key));
        }
        return result;
    }

    /**
     * dot product of fv1 and fv2
     * @param fv1 feature vector 1
     * @param fv2 feature vector 2
     * @return sum of each element
     */
    public static double dotProduct(FeatureVector fv1, FeatureVector fv2){
        if (fv1==null||fv2==null) return 0.0;
        if(fv1.size()>fv2.size()){
            FeatureVector temp = fv2;
            fv2 = fv1;
            fv1 = temp;
        }
        Iterator<Integer> it = fv1.getKey();
        double sum = 0.0;
        while(it.hasNext()){
            int key = it.next();
            sum += fv1.get(key)*fv2.get(key);
        }
        return sum;
    }

    /**
     * each element of v1 multiply that of fv2,
     * @param fv1 feature vector 1
     * @param fv2 feature vector 2
     * @return we store all values except zeros in the new vector
     */
    public static FeatureVector vectorProduct(FeatureVector fv1, FeatureVector fv2){
        if (fv1==null||fv2==null) return null;
        Iterator<Integer> it = fv1.getKey();
        FeatureVector result = new FeatureVector();
        while(it.hasNext()){
            int key = it.next();
            double sum = fv1.get(key)*fv2.get(key);
            if (sum!=0)
                result.add(key, sum);
        }
        return result;
    }

    /**
     * cross product of two vectors
     * @param fv1 vector1
     * @param fv2 vector2
     * @return matrix
     */
    public static FeatureMatrix crossProduct(FeatureVector fv1, FeatureVector fv2){
        if(fv1==null||fv2==null) return null;
        FeatureMatrix fm = new FeatureMatrix();
        Iterator<Integer> it_row = fv1.getKey();
        while(it_row.hasNext()){
            int row_id = it_row.next();
            Iterator<Integer> it_col = fv2.getKey();
            while(it_col.hasNext()){
                int col_id = it_col.next();
                double value = fv1.get(row_id)*fv2.get(col_id);
                fm.addBlock(row_id,col_id,value);
            }
        }
        return fm;
    }

    /**
     * add fv1 to result, in order to save space and time,
     * we do not create new FeatureVector, just add fv1 to result.
     * @param fv1 be added
     * @param result add fv1 to result.
     */
    public static void sum(FeatureVector fv1, FeatureVector result){
        if (fv1==null||result==null) return;
        Iterator<Integer> it = fv1.getKey();
        while(it.hasNext()){
            int key = it.next();
            double sum = fv1.get(key) + result.get(key);
            if(sum!=0.0)
                result.add(key, sum);
            else
                result.remove(key);
        }
    }
}
