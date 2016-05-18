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

// scalastyle:off println
//package org.apache.spark.examples.mllib
package gov.ornl.rheaexamples

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.mllib.linalg.Vectors

/**
 * Compute the singular value decomposition (SVD) of a tall-and-skinny matrix.
 *
 * The input matrix must be stored in row-oriented dense format, one line per row with its entries
 * separated by space. For example,
 * {{{
 * 0.5 1.0
 * 2.0 3.0
 * 4.0 5.0
 * }}}
 * represents a 3-by-2 matrix, whose first row is (0.5, 1.0).
 */
object TallSkinnySVD {
  def main(args: Array[String]) {
    if (args.length != 1) {
      System.err.println("Usage: TallSkinnySVD <input>")
      System.exit(1)
    }

    val time1: Long = System.currentTimeMillis();
    
    val conf = new SparkConf().setAppName("TallSkinnySVD")
    val sc = new SparkContext(conf)

    // Load and parse the data file.
    val rows = sc.textFile(args(0)).map { line =>
      val values = line.split(' ').map(_.toDouble)
      Vectors.dense(values)
    }
    val mat = new RowMatrix(rows)

    val time2: Long = System.currentTimeMillis();
    
    // Compute SVD.
    //val svd = mat.computeSVD(mat.numCols().toInt)
    val svd = mat.computeSVD(50)

    val time3: Long = System.currentTimeMillis();

	val singular = svd.s

    val time4: Long = System.currentTimeMillis();
    
    val u = svd.U
    
    val time5: Long = System.currentTimeMillis();
    
    val v = svd.V
    
    val time6: Long = System.currentTimeMillis();
    
    //mat.saveAsTextFile("/Users/8xo/software/examples_spark_on_rhea/SparkOnRheaExamples/src/main/java/gov/ornl/rheaexamples/output")
    
    val time7: Long = System.currentTimeMillis();
    
    println("Num cols: " + mat.numCols().toInt)
    //println("Singular values are " + singular)
    println("time1 before load " + time1)
    println("time2 after load " + time2)
    println("loading time: " + (time2-time1))
    println("time3 after computeSVD " + time3)
    println("computing time for SVD: " + (time3-time2))
    println("time4 after singular values " + time4)
    println("singluar values: " + (time4-time3))
    println("time5 after finding u " + time5)
    println("time6 after finding v " + time6)
    println("time7 after flusing to disk " + time7)
    
    
    println("End svd")

    sc.stop()
  }
}
// scalastyle:on println
