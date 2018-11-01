import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JButton;


public class EdgeDetect extends JButton{
	
	private Image img;
	private BufferedImage bimg;
	private Font font = new Font("Arial", Font.BOLD, 15);
	private Color white = new Color(255,255,255);
	private Color black = new Color(0,0,0);
	
	public EdgeDetect() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("EdgeDetect");
		this.setFont(font);
		this.setForeground(white);
		this.setBounds(180, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new EdgeListener());
	}
	
	private class EdgeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			bimg = deepCopy(Main.orgbimg);
			edgedetect();
			img = bimg.getScaledInstance(Main.orgimg.getWidth(null), Main.orgimg.getHeight(null), Image.SCALE_SMOOTH);
			Main.aft.drawimage(img);
		}
		
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public void edgedetect() {
		ArrayList<Integer> edgex = new ArrayList<Integer>();
		ArrayList<Integer> edgey = new ArrayList<Integer>();

		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 1; y < Main.orgbimg.getHeight() - 1; y++) {
				int current = Main.orgbimg.getRGB(x, y);
				int  next = Main.orgbimg.getRGB(x, y + 1);
				if (Math.abs(next - current) > 900000) {
					edgex.add(x);
					edgey.add(y);
				}
			}
		}
		for (int y = 0; y < Main.orgbimg.getHeight(); y++) {
			for (int x = 1; x < Main.orgbimg.getWidth() - 1; x++) {
				int current = Main.orgbimg.getRGB(x, y);
				int  next = Main.orgbimg.getRGB(x + 1, y);
				if (Math.abs(next - current) > 900000) {
					edgex.add(x);
					edgey.add(y);
				}
			}
		}
		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 1; y < Main.orgbimg.getHeight(); y++) {
				bimg.setRGB(x, y, white.getRGB());
			}
		}
		for (int i = 0; i < edgex.size(); i++)
			bimg.setRGB(edgex.get(i), edgey.get(i), black.getRGB());
	}
}
