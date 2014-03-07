package cs475.models.classifiers.OnlineLearning;

import cs475.models.classifiers.Classifier;
import cs475.models.classifiers.LinkFunction;
import cs475.CommandLineUtilities;

/**
 * Created by nx on 3/2/14.
 */
public abstract class OnlineLearningClassifier extends Classifier {
    //the training times of online learning methods
    protected int _online_training_iterations=5;
    public OnlineLearningClassifier(LinkFunction linkFunction){
        super(linkFunction);
        if (CommandLineUtilities.hasArg("online_training_iterations"))
            _online_training_iterations = CommandLineUtilities.getOptionValueAsInt("online_training_iterations");
    }

}
