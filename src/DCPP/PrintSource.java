package DCPP;


/*
 * Author:Songhui Yue
 * 2014-10-04
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintSource {
	public static void main(String args[]) throws IOException{
		BufferedReader in 
			= new BufferedReader(new FileReader(args[0]));
		String line = in.readLine();
		while(line != null)
		{		
		  System.out.println(line);
		  line = in.readLine();
		}
		in.close();
	}
}
