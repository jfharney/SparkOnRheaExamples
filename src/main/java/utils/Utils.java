package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Utils {

	public static double MIN_RANGE = 0.;
	public static double MAX_RANGE = 25.0;
	public static String FILE_DIR = "/Users/8xo/software/examples_spark_on_rhea/SparkOnRheaExamples/src/main/java/gov/ornl/rheaexamples/";
	
	public static void main(String [] args) {
		System.out.println("First Example");
		int seed = 2;
		int num_rows = 5000;
		int num_cols = 3500;
		double [][] arr = makeRandom2DArray(seed,num_rows,num_cols);
		for(int i=0;i<arr.length;i++) {
			//System.out.println(Arrays.toString(arr[i]));
		}
		 write2dArrayToFile(arr,num_rows,num_cols);
	}
	
	public static void write2dArrayToFile(double [] [] arr,int num_rows,int num_cols) {
		
		
		
		try {
			File file = new File(FILE_DIR + "generated_matrixR" + num_rows + "C" + num_cols + ".data");
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			
			
			for(int i=0;i<arr.length;i++) {
				StringBuilder sb = new StringBuilder();
				for(int j=0;j<arr[i].length;j++) {
					sb.append(arr[i][j] + " ");
					
				}
				sb.append("\n");
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				//String content = "the text\n";
				String content = sb.toString();
				out.write(content);
				//out.write("new " + content);
				out.close();
				bw.close();
				fw.close();
			}
				

			System.out.println("Done");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static double [][] makeRandom2DArray(int seed,int num_rows,int num_cols) {

		double [][] arr = new double[num_rows][num_cols];
		Random rand = new Random();
		
		if(seed > 0) {
			rand = new Random(seed);
		}
		
		for(int i=0;i<num_rows;i++) {
			for(int j=0;j<num_cols;j++) {
				
				double randomValue = MIN_RANGE + (MAX_RANGE - MIN_RANGE) * rand.nextDouble();
				arr[i][j] = randomValue;
				
			}
		}
		 
		
	    return arr;

	}
	
	
	
}
