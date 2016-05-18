package gov.ornl.rheaexamples;

// $example on$
import java.util.LinkedList;
// $example off$

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
// $example on$
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.SingularValueDecomposition;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
// $example off$

/**
 * Example for SingularValueDecomposition.
 */
public class JavaSVDExample {
  public static void main(String[] args) {
    SparkConf conf = new SparkConf().setAppName("SVD Example");
    SparkContext sc = new SparkContext(conf);
    JavaSparkContext jsc = JavaSparkContext.fromSparkContext(sc);

    // $example on$// $example on$
    //double[][] array = {{1.12, 2.05, 3.12}, {5.56, 6.28, 8.94}, {10.2, 8.0, 20.5}};
    int seed = 2;
    int num_rows = 30;
    int num_cols = 10000;
    double [] [] array = Utils.makeRandom2DArray(seed, num_rows, num_cols);
    LinkedList<Vector> rowsList = new LinkedList<>();
    for (int i = 0; i < array.length; i++) {
      Vector currentRow = Vectors.dense(array[i]);
      rowsList.add(currentRow);
    }
    JavaRDD<Vector> rows = jsc.parallelize(rowsList);

    // Create a RowMatrix from JavaRDD<Vector>.
    RowMatrix mat = new RowMatrix(rows.rdd());

    // Compute the top 3 singular values and corresponding singular vectors.
    SingularValueDecomposition<RowMatrix, Matrix> svd = mat.computeSVD(3, true, 1.0E-9d);
    RowMatrix U = svd.U();
    Vector s = svd.s();
    Matrix V = svd.V();
    
    // $example off$
    Vector[] collectPartitions = (Vector[]) U.rows().collect();
    System.out.println("U factor is:");
    for (Vector vector : collectPartitions) {
      System.out.println("\t" + vector);
    }
    System.out.println("Singular values are: " + s);
    System.out.println("V factor is:\n" + V);

    jsc.stop();
  }
  
}
