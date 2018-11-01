import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Sliders extends JPanel{
	private Image img;
	private static final int MIN = -50;
	private static final int MAX = 50;
	private static final int INIT = 0;
	
	BufferedImage bimg;
	int cnt = 0;
	
	private JSlider bright = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);
	private JSlider contrast = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);
	private JLabel brightl = new JLabel("Bright");
	private JLabel contrastl = new JLabel("Contrast");
	
	private Font font = new Font("Arial", Font.BOLD, 15);
	
	public void setsliders() {
		this.setBackground(new Color(100,100,100));
		bright.setPaintTicks(true);
		bright.setMajorTickSpacing((MAX - MIN)/4);
		bright.setBackground(new Color(100,100,100));
		bright.addChangeListener(new SliderListener());
		bright.addMouseListener(new SliderMouseListener());
		bright.setSize(250, 100);
		
		contrast.setPaintTicks(true);
		contrast.setMajorTickSpacing((MAX - MIN)/4);
		contrast.setBackground(new Color(100,100,100));
		contrast.addChangeListener(new SliderListener());
		contrast.addMouseListener(new SliderMouseListener());
		contrast.setSize(250, 100);
	}
	
	public void setlabels() {
		brightl.setForeground(Color.white);
		brightl.setFont(font);
		contrastl.setForeground(Color.white);
		contrastl.setFont(font);
	}
	
	public Sliders() {
		this.setBounds(Main.Width*4/5 - 350, 50, 250, 300);
		this.setLayout(new GridLayout(4,0));
		setlabels();
		setsliders();
		this.add(brightl);
		this.add(bright);
		this.add(contrastl);
		this.add(contrast);
	}
	
	public class SliderMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
				bimg = deepCopy(Main.orgbimg);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			
			if (Main.orgimg == null) {
				JOptionPane.showMessageDialog(null, "Select your image file first!", "WARNING", JOptionPane.WARNING_MESSAGE);
				return;
			}
			JSlider sli = (JSlider)e.getSource();
			if (sli.equals(bright))
				BrigthAdjust(sli.getValue());
			else if(sli.equals(contrast))
				ContrastAdjust(sli.getValue());
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
	
	public void ContrastAdjust(int level) {
		float k;
		if (level > 0) 
			k = (float) (50.0/(50 - level));
		else
			k = (float)((50 + level)/50.0);
		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 0; y < Main.orgbimg.getHeight(); y++) {
				Color c = new Color(Main.orgbimg.getRGB(x, y));
				float r = 128 + k*(c.getRed()-128);
				float g = 128 + k*(c.getGreen()-128);
				float b = 128 + k*(c.getBlue()-128);
				r = r  < 0? 0 : Math.min(255, r);
				g = g  < 0? 0 : Math.min(255, g);
				b = b  < 0? 0 : Math.min(255, b);
				bimg.setRGB(x, y, new Color(r/255, g/255, b/255).getRGB());
			}
		}
	}
	
	public void BrigthAdjust(int level) {
		for (int x = 0; x < Main.orgbimg.getWidth(); x++) {
			for (int y = 0; y < Main.orgbimg.getHeight(); y++) {
				Color c = new Color(Main.orgbimg.getRGB(x, y));
				int r = c.getRed() + 2*(level);
				int g = c.getGreen() + 2*(level);
				int b = c.getBlue() + 2*(level);
				r = r  < 0? 0 : Math.min(255, r);
				g = g  < 0? 0 : Math.min(255, g);
				b = b  < 0? 0 : Math.min(255, b);
				bimg.setRGB(x, y, new Color(r,g,b).getRGB());
			}
		}
	}
}
