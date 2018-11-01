import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;

public class GrayScale extends JButton {
	private Image img;
	private BufferedImage bimg;
	private Font font = new Font("Arial", Font.BOLD, 15);
	
	public GrayScale() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("GrayScale");
		this.setFont(font);
		this.setForeground(Color.white);
		this.setBounds(30, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new GrayListener());
	}
	
	public class GrayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			bimg = deepCopy(Main.orgbimg);
			toGrayScale();
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
	
	public void toGrayScale() {
		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 0; y < Main.orgbimg.getHeight(); y++) {
				Color gray = new Color(Main.orgbimg.getRGB(x,y));
				int Y = (int) (0.2126 * gray.getRed() + 0.7152 * gray.getGreen() + 0.0722 * gray.getBlue());
				bimg.setRGB(x, y, new Color(Y, Y, Y).getRGB());
			}
		}
	}
}
