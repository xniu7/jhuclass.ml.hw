package cs475;

import cs475.Instance;
import cs475.Predictor;

import java.util.List;

public abstract class Evaluator {

	public abstract double evaluate(List<Instance> instances, Predictor predictor);
}
