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
	private final int STONE_SIZE = 128;
	private final int WIDTH = 350, HEIGHT = 800;
	
	BufferedImage bimg;
	Image black;
	Image white;
	private BasicStroke stroke = new BasicStroke(8);
	private Color other = new Color(126, 118, 113);

	private Winner winner = new Winner();
	private GameChatPanel gcp = new GameChatPanel();
	public static GameUndo gu = new GameUndo();
	
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
		this.setBounds(800, 0, WIDTH, HEIGHT);
		this.setBackground(new Color(160,160,160));
		this.add(winner);
		this.add(gcp);
		this.add(gu);
	}
	
	public Winner getwinner() {return winner;}
	public GameChatPanel getgcp() {return gcp;}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(black, 24, 57, null);
		g.drawImage(white, 200, 57, null);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		if (GamePanel.Turn.equals(GamePanel.myColor)) {
			g2.setColor(Color.white);
			gu.setenable(true);
		}
		else {
			g2.setColor(other);
			gu.setenable(false);
		}
		
		if (GamePanel.Turn.equals("Black"))		//show who's turn is
			g2.drawRect(12, 37, 152, 168);
		else if (GamePanel.Turn.equals("White"))
			g2.drawRect(186, 37, 152, 168);
	}
}
