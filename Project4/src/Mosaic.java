import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;

public class Mosaic extends JButton {
	private Image img;
	private BufferedImage bimg;
	private Font font = new Font("Arial", Font.BOLD, 15);
	int factor = 3;
	
	public Mosaic() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("Mosaic");
		this.setFont(font);
		this.setForeground(Color.white);
		this.setBounds(630, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new mosaicListener());
	}
	
	public class mosaicListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			bimg = deepCopy(Main.orgbimg);
			mosaic();
			img = bimg.getScaledInstance(Main.orgimg.getWidth(null), Main.orgimg.getHeight(null), Image.SCALE_SMOOTH);
			Main.aft.drawimage(img);
		}
	}
	
	public void mosaic() {
		for (int x = factor/2; x < Main.orgbimg.getWidth() - (factor/2); x+=factor) {
			for (int y = factor/2; y < Main.orgbimg.getHeight() - (factor/2); y+=factor) {
				int c = Main.orgbimg.getRGB(x, y);
				for (int i = -(factor/2); i <= factor/2; i++) {
					for (int j = -(factor/2); j <= factor/2; j++)
						bimg.setRGB(x + i, y + j, c);
				}
			}
		}
		factor+=2;
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
