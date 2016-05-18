/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gov.ornl.rheaexamples;

import java.util.Arrays;
// $example on$
import java.util.LinkedList;
// $example off$


import scala.Tuple2;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
// $example on$
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.mllib.feature.PCA;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;

// $example off$

/**
 * Example for compute principal components on a 'RowMatrix'.
 */
public class JavaPCAExample {
	
	public static String INPUT_FILE = "/Users/8xo/software/examples_spark_on_rhea/SparkOnRheaExamples/src/main/java/gov/ornl/rheaexamples/lpsa.data";
	public static String OUTPUT_FILE = "/Users/8xo/software/examples_spark_on_rhea/SparkOnRheaExamples/src/main/java/gov/ornl/rheaexamples/lpsa.output.data";
	
  public static void main(String[] args) {

	SparkConf jconf = new SparkConf().setMaster("local").setAppName("PCA Example");
	JavaSparkContext jsc = new JavaSparkContext(jconf);

	JavaRDD<String> input = jsc.textFile(INPUT_FILE);
	
	///Thread.sleep(6000);
	
	
	JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() {
		  public Iterable<String> call(String s) { return Arrays.asList(s.split(",")); }
		});
    
	System.out.println("words: " + words);
	
	JavaPairRDD<String,Integer> pairs = words.mapToPair(new PairFunction<String,String,Integer>() {
		public Tuple2<String,Integer> call(String s) {
			return new Tuple2<String,Integer>(s,1);
		}
	});
	
	JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
		  public Integer call(Integer a, Integer b) { return a + b; }
		});
	
	//counts.saveAsTextFile(OUTPUT_FILE);
	
    /*
    JavaRDD<String> input = sc.textFile(INPUT_FILE);
    
    
    JavaRDD<String> parts = input.Map(
    		new MapFunction<String,String>() {
    			public Iterable<String> call(String x) {
    				return Arrays.asList(x.split(","));
    			}
    		}
    		);
    */
    
    
    /*
    String inputFile = sc.textFile();
    JavaRDD<String> input = sc.textFile(INPUT_FILE);
    JavaRDD<String> parts = input.flatMap(
    		new FlatMapFunction<String,String>() {
    			public Iterable<String> call(String x) {
    				return Arrays.asList(x.split(","));
    			}
    		}
    		);
    
    
    JavaRDD<LabeledPoint> points = words.map(
    		new MapFunction<String,String>()
    		);
    
    val data = sc.textFile("data/mllib/ridge-data/lpsa.data").map { line =>
    val parts = line.split(',')
    LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
  }.cache()
    */
    
    
    
    
    /*
	SparkConf conf = new SparkConf().setMaster("local").setAppName("PCA Example");
	SparkContext sc = new SparkContext(conf);


    
    // $example on$
    //double[][] array = {{1.12, 2.05, 3.12}, {5.56, 6.28, 8.94}, {10.2, 8.0, 20.5}};
    int seed = 2;
    int num_rows = 300;
    int num_cols = 100;
    //double [] [] array = Utils.makeRandom2DArray(seed, num_rows, num_cols);
    double[][] array = {{1.12, 2.05, 3.12}, {5.56, 6.28, 8.94}, {10.2, 8.0, 20.5}};
    LinkedList<Vector> rowsList = new LinkedList<>();
    for (int i = 0; i < array.length; i++) {
      Vector currentRow = Vectors.dense(array[i]);
      rowsList.add(currentRow);
    }
    JavaRDD<Vector> rows = JavaSparkContext.fromSparkContext(sc).parallelize(rowsList);

    // Create a RowMatrix from JavaRDD<Vector>.
    RowMatrix mat = new RowMatrix(rows.rdd());

    // Compute the top 3 principal components.
    Matrix pc = mat.computePrincipalComponents(3);
    RowMatrix projected = mat.multiply(pc);
    // $example off$
    Vector[] collectPartitions = (Vector[])projected.rows().collect();
    System.out.println("Projected vector of principal component:");
    for (Vector vector : collectPartitions) {
      System.out.println("\t" + vector);
    }
    */
  }
}