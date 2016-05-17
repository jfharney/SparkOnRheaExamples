package gov.ornl.rheaexamples;

import java.util.Arrays;
import java.util.Random;

public class Utils {

	public static double MIN_RANGE = 0.;
	public static double MAX_RANGE = 25.0;
	
	public static void main(String [] args) {
		System.out.println("First Example");
		int seed = 2;
		int num_rows = 5;
		int num_cols = 4;
		double [][] arr = makeRandom2DArray(seed,num_rows,num_cols);
		for(int i=0;i<arr.length;i++) {
			System.out.println(Arrays.toString(arr[i]));
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
