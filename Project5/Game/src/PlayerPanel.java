import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//player panel 
public class PlayerPanel extends JPanel{
	public final static int STONE_SIZE = 128;
	
	int width = 350, height = 800;
	BufferedImage bimg;
	Image black;
	Image white;
	BasicStroke stroke = new BasicStroke(8);

	public static Winner winner = new Winner();
	
	public PlayerPanel() {
		try {
			bimg = ImageIO.read(new File(".\\src\\Black.png"));
			black = bimg.getScaledInstance(STONE_SIZE,STONE_SIZE, Image.SCALE_SMOOTH);
			
			bimg = ImageIO.read(new File(".\\src\\White.png"));
			white = bimg.getScaledInstance(STONE_SIZE,STONE_SIZE, Image.SCALE_SMOOTH);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.setLayout(null);
		this.setBounds(800, 0, width, height);
		this.setBackground(new Color(160,160,160));
		this.add(winner);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(black, 24, 57, null);
		g.drawImage(white, 200, 57, null);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		g2.setColor(Color.white);
		
		if (GamePanel.isBlack % 4 > 1)		//show who's turn is
			g2.drawRect(12, 37, 152, 168);
		else
			g2.drawRect(186, 37, 152, 168);
	}
}
