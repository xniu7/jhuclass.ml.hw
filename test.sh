#!/bin/bash

algorithm=margin_perceptron
data=('bio' 'finance' 'nlp' 'speech' 'easy' 'hard' 'vision' 'circle')
n=${#data[@]}
class_path=../out
class_name=cs475.Classify

for ((i=0;i<$n;i++))
do
   data_train_path=/home/nx/Documents/classes/machine-learning/data/${data[i]}/${data[i]}.train
   data_dev_path=/home/nx/Documents/classes/machine-learning/data/${data[i]}/${data[i]}.dev
   model_path=../models/${data[i]}.$algorithm.model
   prediction_path=../predictions/${data[i]}.dev.predictions

   java -cp $class_path $class_name -mode train -algorithm $algorithm -model_file $model_path -data $data_train_path -gd_eta 0.01 -gd_iterations 20 -num_features_to_select 20 -online_learning_rate 1.0 -polynomial_kernel_exponent 2 -online_training_iterations 5
   java -Xmx128m -cp $class_path $class_name -mode test -model_file $model_path -data $data_dev_path -predictions_file $prediction_path
done
