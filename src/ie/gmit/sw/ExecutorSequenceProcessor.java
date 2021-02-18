package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.*;

/**
 * @author Emil
 *
 */
public class ExecutorSequenceProcessor {
	private Map<Integer, File> subject = new ConcurrentSkipListMap<>(); 
	
	public record Result (int id, BufferedImage value, String test){} //A record stores the result of the image parsing

	
	
	/**
	 * @param outPutFile
	 * @param filter
	 * @param col
	 * @throws Exception
	 */
	public void process(String outPutFile, double[][] filter, Collection<Future<Result>> col) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(col.isEmpty()? 1:col.size()); //Start a thread pool of size poolSize
		
		Set<Integer> ids = subject.keySet(); 
		
		for (Integer id : ids) { //For each sequence ID in the map
			//send every each Callable to the thread pool and immediately gets the Future(promise)
			Future<Result> result = es.submit(new Filtering(id, subject.get(id), filter ));
			col.add(result); //Add the Future to the results collection
		}
		
		System.out.println("Tasks submitted processing results....");
		
		for (Future<Result> fr : col) { //Iterate over the futures to get the result values
			Result r = fr.get(); 
		     //Output the result
			ImageIO.write(r.value(), "png", new File(outPutFile+"/updated"+r.test()));
		}
		System.out.println("Finished - shutting down thread pool.");
		es.shutdown(); //stopping the thread pool after all the threads are done
		
		es.awaitTermination(5, TimeUnit.SECONDS); //waiting 5 and then shutdown
		System.out.println("Done.");
	}
	
	
	/**
	 * @param fileName
	 * @throws Exception
	 */
	public void parse(String fileName) throws Exception {
	
		try {
			int counter = 0;
		
			
			File pictureDir = new File(fileName);			//confirm selection
			System.out.println(fileName + " contains: /n");
			
			String[] pic_pathnames = pictureDir.list();			//print list of files to be modified
			for(String pic_path : pic_pathnames) {
				System.out.println(pic_path);
			}
			
			for(File pic : pictureDir.listFiles()) {
				counter++;                  	//going through the list of the files
				if(pic.isFile())//checking if its file
				subject.put(counter, pic); 	//Add the counter as a key in the map and the and pic as value
			}
		}
		catch(Exception e) {
			System.out.println(fileName + " can't be access");
			e.printStackTrace();
		}
		
	}
	
}