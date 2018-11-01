import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//load the image of stone
public class Stone {
	public final static int STONE_SIZE = 40;
	
	public static Image black;
	public static Image white;
	BufferedImage bimg;
	
	private String bsfname = ".\\src\\Black.png";
	private String wsfname = ".\\src\\White.png";
		
	public Stone() {
		try {
			bimg = ImageIO.read(new File(bsfname));
			black = bimg.getScaledInstance(STONE_SIZE, STONE_SIZE, Image.SCALE_SMOOTH);
			
			bimg = ImageIO.read(new File(wsfname));
			white = bimg.getScaledInstance(STONE_SIZE, STONE_SIZE, Image.SCALE_SMOOTH);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}