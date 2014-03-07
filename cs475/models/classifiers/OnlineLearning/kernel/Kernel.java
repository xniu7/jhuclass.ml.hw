package cs475.models.classifiers.OnlineLearning.kernel;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import cs475.Instance;
import cs475.FeatureVector;
import cs475.matrix.FeatureMatrix;
import cs475.matrix.Matrix;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nx on 3/2/14.
 */
public abstract class Kernel implements Serializable{
    //protected FeatureMatrix _gramMatrix = new FeatureMatrix();
    protected Matrix _gramMatrix;

    public void init(List<Instance> instances){
        if(instances==null) return;
        _gramMatrix = new Matrix(instances.size(),instances.size());
        for(int i=0;i<instances.size();i++){
            for(int j=0;j<instances.size();j++){
                double ker = function(instances.get(i).getFeatureVector(),instances.get(j).getFeatureVector());
                this._gramMatrix.set(i, j, ker);
            }
        }
    }

    public abstract double function(FeatureVector x, FeatureVector y);

    public double get(int row_id, int col_id){
        return this._gramMatrix.get(row_id,col_id);
    }
    public void release(){
        this._gramMatrix=null;
    }
}
