import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	
	public static Image orgimg;
	public static BufferedImage orgbimg;
	
	public static Before org = new Before();
	public static After aft = new After();
	
	EdgeDetect ed = new EdgeDetect();
	GrayScale gs = new GrayScale();
	Recovery rc = new Recovery();
	Sliders sli = new Sliders();
	CloseUp cl = new CloseUp();
	Mosaic ms = new Mosaic();
	Blur bl = new Blur();
	Invert iv = new Invert();
	
	public void setframe() {
		this.setLayout(null);
		this.getContentPane().setBackground(new Color(100,100,100));
		this.setBounds(Width/10, Height/10, Width*4/5, Height*4/5);
		this.addMouseListener(new BeforeMouseListener());
	}
	
	public void setpanel() {
		aft.setBounds(org.getX() + org.getWidth() + 20, org.getY(), org.getWidth(), org.getHeight());
		this.add(org);
		this.add(aft);
	}
	
	public Main() {
		setframe();
		setpanel();
		this.add(gs);
		this.add(ed);
		this.add(rc);
		this.add(ms);
		this.add(bl);
		this.add(iv);
		this.add(sli);
		this.add(cl.closeup);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class BeforeMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (org.getBounds().contains(e.getPoint())) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg","png");
				fc.setMultiSelectionEnabled(false);
				fc.setFileFilter(filter);
				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file= fc.getSelectedFile();
					try {
						orgbimg = ImageIO.read(file);
						orgimg = orgbimg.getScaledInstance(org.getWidth() - 30, org.getHeight() - 20, Image.SCALE_SMOOTH);
						org.drawimage(orgimg);
						aft.drawimage(orgimg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
	}
}