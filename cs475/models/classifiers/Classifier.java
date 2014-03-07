package cs475.models.classifiers;

import cs475.FeatureVector;
import cs475.Predictor;

/**
 * Created by nx on 2/16/14.
 */
public abstract class Classifier extends Predictor {

    protected LinkFunction _linkFunction;
    protected FeatureVector _w = new FeatureVector();

    public Classifier(LinkFunction linkFunction){
        this._linkFunction = linkFunction;
    }

}
