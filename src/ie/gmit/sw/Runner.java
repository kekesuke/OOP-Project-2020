package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Future;

import ie.gmit.sw.ExecutorSequenceProcessor.Result;

/**
 * @author Emil
 *
 */

public class Runner {
	public static Scanner console = new Scanner(System.in);
	
	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {

		double[][] EDGE_DETECTIONTWO = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
		double[][] LAPLACIAN = { { 1, 0, -1 }, { 0, 0, 0 }, { -1, 0, 1 } };
		double[][] SHARPEN = { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };
		double[][] DIAGONAl_LINES = { {-1, -1, -1},{2, 2, 2},{-1, -1, -1} };
		double[][] BOX_BLUR = {{ 00.111, 0.111, 0.111},{0.111, 0.111, 0.111},{0.111, 0.111, 0.111}};
		double[][] filter={ { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };;
		int choice = 0;
		String inputFolder;
		String outPutFolder= null;
		
			
			
			ExecutorSequenceProcessor runner = new ExecutorSequenceProcessor();
			
		System.out.println("****************************************************");
		System.out.println("* Image Filtering System                           *");
		System.out.println("* 1 Enter Image Directory                          *");
		System.out.println("* 2 Add Custom Filter                              *");
		System.out.println("* 3 Quit                                           *");
		System.out.println("*  Select option [1-4]                             *");
		System.out.println("****************************************************");
		System.out.print("Please Enter Text>");
		choice = console.nextInt();
		
		if(choice == 1) {
			BufferedReader folderIn = new BufferedReader(new InputStreamReader(System.in));		//enter the folder your getting your photos from
			System.out.println("Enter input folder path");
			inputFolder = folderIn.readLine();
		
			BufferedReader folderOut = new BufferedReader(new InputStreamReader(System.in));	//enter the folder you want to output your modified photos to
			System.out.println("Enter output folder path");
			outPutFolder = folderOut.readLine();
			
			
			//A collection of Future objects to store the results of each Callable
			//Use a thread pool of size 3 to compare the following query sequence again the subject map db
			System.out.println("****************************************************");
			System.out.println("* 1 EDGE_DETECTION                                 *");
			System.out.println("* 2 LAPLACIAN Filter                               *");
			System.out.println("* 3 SHARPEN Filter                                 *");
			System.out.println("* 4 DIAGONAl_LINESr Quit                           *");
			System.out.println("* 5 BOX_BLUR                                       *");
			System.out.println("* 6 Select option [1-5] 6 for exit                 *");
			System.out.println("****************************************************");
			System.out.print("Please Enter Filter>");
			choice = console.nextInt();
			switch (choice) {	
			case 1:
				filter = EDGE_DETECTIONTWO;
				break;
			case 2:
				filter = LAPLACIAN;
				break;
			case 3:
				filter = SHARPEN;
				break;
			case 4:
				filter = DIAGONAl_LINES;
				break;
			case 5:
				filter = BOX_BLUR;
				break;
			default:
				break;
			}
			runner.parse(inputFolder);
		}else if(choice==2){
			System.out.println("****************************************************");
			System.out.println("* 1 EDGE_DETECTION                                 *");
			System.out.println("* 2 LAPLACIAN Filter                               *");
			System.out.println("* 3 SHARPEN Filter                                 *");
			System.out.println("* 4 DIAGONAl_LINESr Quit                           *");
			System.out.println("* 5 BOX_BLUR                         			   *");
			System.out.println("  6 Select option [1-5] 6 for exit 				   *");
			System.out.println("****************************************************");
			System.out.print("Please Enter > ");
			choice = console.nextInt();
			
			switch (choice) {	
			case 1:
				filter = EDGE_DETECTIONTWO;
				break;
			case 2:
				filter = LAPLACIAN;
				break;
			case 3:
				filter = SHARPEN;
				break;
			case 4:
				filter = DIAGONAl_LINES;
				break;
			case 5:
				filter = BOX_BLUR;
				break;
			default:
				break;
			}
			
		}else if(choice==3) {
			System.out.print("Please Select  > ");
			choice = console.nextInt();
			
			switch (choice) {	
			case 1:
				filter = EDGE_DETECTIONTWO;
				break;
			case 2:
				filter = LAPLACIAN;
				break;
			case 3:
				filter = SHARPEN;
				break;
			case 4:
				filter = DIAGONAl_LINES;
				break;
			case 5:
				filter = BOX_BLUR;
				break;
			default:
				break;
			}
		}
		Collection<Future<Result>> col = new ArrayList<>();
		
		runner.process(outPutFolder,filter, col);
		
	

	}

}
