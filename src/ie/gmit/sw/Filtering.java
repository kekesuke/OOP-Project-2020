package ie.gmit.sw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import ie.gmit.sw.ExecutorSequenceProcessor.Result;

/**
 * @author Emil
 *
 */
public class Filtering implements Callable<Result>{ //Parameterise Callable<T> with a Result
	private int id; //The subject sequence number
	private File file; //The subject sequence
	private double filter[][] = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};

	
	/**
	 * @param id
	 * @param file
	 * @param effect
	 */
	public Filtering(int id, File file, double[][] effect) {
		this.id = id;
		this.file = file;
		this.filter = effect;
		
	}
	
	/**
	 * @param pixel
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 */
	public int applyFilter(int pixel, int x, int y) throws Exception {
		
		
		
		int element=pixel;
		
		int surrounding_pix[][] = {{0,0,0},{0,0,0},{0,0,0}};
		for(int row = 0; row < filter.length; row++){
			for(int col = 0; col < filter[row].length; col++) {
				
				BufferedImage image = ImageIO.read(file);	
				element = image.getRGB(x+col, y+row);
				surrounding_pix[row][col] = element;
				
			}
		}
		
		int final_pix = 0;
		for(int row = 0; row < filter.length; row++){
			for(int col = 0; col < filter[row].length; col++) {
				
				final_pix += filter[row][col] * surrounding_pix[row][col];
				
			}
		}
		element = final_pix;
		return element;
	}
	
	/**
	 *
	 */
	@Override
	public Result call() throws Exception {
		//Implement the Levenshtein distance algorithm in the threaded call() method
		BufferedImage renderedPic=null;
		try {
			
			  renderedPic = ImageIO.read(file);	//using BufferedImage going throught the pixels 
			
			for(int y = 0; y < renderedPic.getHeight(); y++) {
				for(int x = 0; x < renderedPic.getWidth(); x++) {//Filtering in 2d
					
					try {
						int pixel = renderedPic.getRGB(x, y);
						renderedPic.setRGB(x, y, applyFilter(pixel, x, y));
					}
					catch(Exception E) {
						continue;
					}
				}
				
			}
			
		}
		catch(Exception IE) {
			IE.printStackTrace();			//print details about the error
		}
		return new Result(id, (BufferedImage) renderedPic, file.getName());// returning new result 
	}		
}
