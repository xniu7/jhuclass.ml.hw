package cs475.test;

import cs475.matrix.FeatureMatrix;
import cs475.FeatureVector;
import cs475.matrix.Matrix;

/**
 * Created by nx on 3/2/14.
 */
public class TestMatrix {
    private static void crossProductTest(){
        FeatureVector fv1 = new FeatureVector();
        FeatureVector fv2 = new FeatureVector();
        fv1.add(1,3); fv1.add(4,4);
        fv2.add(1,2); fv2.add(3,5);
        FeatureMatrix fm = FeatureVector.crossProduct(fv1,fv2);
        System.out.println(fm);
    }

    private static void matrixProductTest(){
        Matrix matrix1 = new Matrix(2,3);
        matrix1.set(0,1,2);
        matrix1.set(1,1,4);
        System.out.println(matrix1);
        Matrix matrix2 = new Matrix(2,3);
        matrix2.set(0,1,2);
        matrix2.set(1,1,4);
        System.out.println(matrix2);
        System.out.println(matrix2.add(matrix1));
    }

    public static void main(String[] args){
        matrixProductTest();
    }
}
