import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;

public class Blur extends JButton {
	private Image img;
	private BufferedImage bimg;
	private Font font = new Font("Arial", Font.BOLD, 15);
	int factor = 3;
	public Blur() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("blur");
		this.setFont(font);
		this.setForeground(Color.white);
		this.setBounds(780, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new blurListener());
	}
	
	public class blurListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			bimg = deepCopy(Main.orgbimg);
			blur();
			img = bimg.getScaledInstance(Main.orgimg.getWidth(null), Main.orgimg.getHeight(null), Image.SCALE_SMOOTH);
			Main.aft.drawimage(img);
		}
	}
	
	public void blur() {
		for (int x = factor/2; x < Main.orgbimg.getWidth() - (factor/2); x++) {
			for (int y = factor/2; y < Main.orgbimg.getHeight() - (factor/2); y++) {
				int rsum = 0;
				int gsum = 0;
				int bsum = 0;
				for (int i = -(factor/2); i <= (factor/2); i++) {
					for (int j = -(factor/2); j <= (factor/2); j++) {
						Color c = new Color(bimg.getRGB(x + i, y + j));
						rsum += c.getRed();
						gsum += c.getGreen();
						bsum += c.getBlue();
					}
				}
				bimg.setRGB(x, y, new Color(rsum/(factor * factor), gsum/(factor * factor), bsum/(factor * factor)).getRGB());
			}
		}
		factor += 2;
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
