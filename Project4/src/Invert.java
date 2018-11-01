import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;


public class Invert extends JButton{
	private Image img;
	private BufferedImage bimg;
	private Font font = new Font("Arial", Font.BOLD, 15);

	public Invert() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("Invert");
		this.setFont(font);
		this.setForeground(Color.white);
		this.setBounds(930, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new InvertListener());
	}
	
	public class InvertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			bimg = deepCopy(Main.orgbimg);
			invert();
			img = bimg.getScaledInstance(Main.orgimg.getWidth(null), Main.orgimg.getHeight(null), Image.SCALE_SMOOTH);
			Main.aft.drawimage(img);
		}
	}
	
	public void invert() {
		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 0; y < Main.orgbimg.getHeight(); y++) {
				Color c = new Color(bimg.getRGB(x, y));
				int r = 255 - c.getRed();
				int g = 255 - c.getGreen();
				int b = 255 -c.getBlue();
				bimg.setRGB(x, y, new Color(r,g,b).getRGB());
			}
		}
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
