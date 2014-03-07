package cs475;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import cs475.evaluators.AccuracyEvaluator;
import cs475.models.classifiers.OnlineLearning.DualPerceptronClassifier;
import cs475.models.classifiers.OnlineLearning.MarginInfusedRelaxationClassifier;
import cs475.models.classifiers.OnlineLearning.MarginPerceptronClassifier;
import cs475.models.predictors.EvenOddPredictor;
import cs475.models.classifiers.LogisticRegression.LogisticRegressionClassifier;
import cs475.models.predictors.MajorityPredictor;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class Classify {
	static public LinkedList<Option> options = new LinkedList<Option>();

    private static void run(String[] args) throws IOException{
        // Parse the command line.
        String[] manditory_args = { "mode"};
        createCommandLineOptions();
        CommandLineUtilities.initCommandLineParameters(args, Classify.options, manditory_args);

        String mode = CommandLineUtilities.getOptionValue("mode");
        String data = CommandLineUtilities.getOptionValue("data");
        String predictions_file = CommandLineUtilities.getOptionValue("predictions_file");
        String algorithm = CommandLineUtilities.getOptionValue("algorithm");
        String model_file = CommandLineUtilities.getOptionValue("model_file");

        System.out.println(data+":");

        if (mode.equalsIgnoreCase("train")) {
            if (data == null || algorithm == null || model_file == null) {
                System.out.println("Train requires the following arguments: data, algorithm, model_file");
                System.exit(0);
            }
            // Load the training data.
            DataReader data_reader = new DataReader(data, true);
            List<Instance> instances = data_reader.readData();
            data_reader.close();

            // Train the models.
            Predictor predictor = train(instances, algorithm);
            saveObject(predictor, model_file);

        } else if (mode.equalsIgnoreCase("test")) {
            if (data == null || predictions_file == null || model_file == null) {
                System.out.println("Train requires the following arguments: data, predictions_file, model_file");
                System.exit(0);
            }

            // Load the test data.
            DataReader data_reader = new DataReader(data, true);
            List<Instance> instances = data_reader.readData();
            data_reader.close();

            // Load the models.
            Predictor predictor = (Predictor)loadObject(model_file);
            evaluateAndSavePredictions(predictor, instances, predictions_file);
        } else {
            System.out.println("Requires mode argument.");
        }


    }
	
	public static void main(String[] args) throws IOException {
        Long t1 = System.currentTimeMillis();
        run(args);
        Long t2 = System.currentTimeMillis();
        System.out.println("execute time: "+1.0*(t2-t1)/1000+" sec");
	}
	

	private static Predictor train(List<Instance> instances, String algorithm) {
        if (instances==null || instances.size()==0) return null;

		// TODO Train the models using "algorithm" on "data"
        Predictor predictor = null;
        if (algorithm.equalsIgnoreCase("majority"))
            predictor = new MajorityPredictor();
        else if(algorithm.equalsIgnoreCase("even_odd"))
            predictor = new EvenOddPredictor();
        else if(algorithm.equalsIgnoreCase("logistic_regression"))
            predictor = new LogisticRegressionClassifier();
        else if(algorithm.equalsIgnoreCase("margin_perceptron"))
            predictor = new MarginPerceptronClassifier();
        else if(algorithm.equalsIgnoreCase("perceptron_linear_kernel")||
                algorithm.equalsIgnoreCase("perceptron_polynomial_kernel"))
            predictor = new DualPerceptronClassifier();
        else if(algorithm.equalsIgnoreCase("mira"))
            predictor = new MarginInfusedRelaxationClassifier();
        else
            return null;

        predictor.train(instances);

		// TODO Evaluate the models
        Evaluator evaluator = new AccuracyEvaluator();
        double accuracy = evaluator.evaluate(instances,predictor);
        System.out.println("train data accuracy is "+accuracy);

        return predictor;
	}

	private static void evaluateAndSavePredictions(Predictor predictor,
			List<Instance> instances, String predictions_file) throws IOException {
		PredictionsWriter writer = new PredictionsWriter(predictions_file);
		// TODO Evaluate the models if labels are available.
        if (predictor==null) {
            System.out.println("predictor is null");
            return;
        }
		Evaluator evaluator = new AccuracyEvaluator();
        double accuracy = evaluator.evaluate(instances,predictor);
        System.out.println(predictions_file+" accuracy is " + accuracy);
		for (Instance instance : instances) {
			Label label = predictor.predict(instance);
			writer.writePrediction(label);
		}
		
		writer.close();
		
	}

	public static void saveObject(Object object, String file_name) {
		try {
			ObjectOutputStream oos =
				new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(new File(file_name))));
			oos.writeObject(object);
			oos.close();
		}
		catch (IOException e) {
			System.err.println("Exception writing file " + file_name + ": " + e);
		}
	}

	/**
	 * Load a single object from a filename. 
	 * @param file_name
	 * @return
	 */
	public static Object loadObject(String file_name) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file_name))));
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (IOException e) {
			System.err.println("Error loading: " + file_name);
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading: " + file_name);
		}
		return null;
	}
	
	public static void registerOption(String option_name, String arg_name, boolean has_arg, String description) {
		OptionBuilder.withArgName(arg_name);
		OptionBuilder.hasArg(has_arg);
		OptionBuilder.withDescription(description);
		Option option = OptionBuilder.create(option_name);
		
		Classify.options.add(option);		
	}
	
	private static void createCommandLineOptions() {
		registerOption("data", "String", true, "The data to use.");
		registerOption("mode", "String", true, "Operating mode: train or test.");
		registerOption("predictions_file", "String", true, "The predictions file to create.");
		registerOption("algorithm", "String", true, "The name of the algorithm for training.");
		registerOption("model_file", "String", true, "The name of the models file to create/load.");
        registerOption("gd_eta", "double", true, "The step size parameter for GD.");
        registerOption("gd_iterations", "int", true, "The number of GD iterations.");
        registerOption("num_features_to_select", "int", true, "The number of features to select.");
        registerOption("online_training_iterations", "int", true, "The number of training iterations for online methods.");
        registerOption("online_learning_rate", "double", true, "The learning rate for perceptron.");
        registerOption("polynomial_kernel_exponent", "int", true, "The exponent of the polynomial kernel.");
        // Other options will be added here.
	}
}
