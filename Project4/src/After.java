import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class After extends JPanel{
	private Image img;
	
	
	private int x =0, y = 0;
	private TitledBorder after = new TitledBorder(new LineBorder(new Color(197,216,255)), "After");
	public static boolean close = false;
	
	public After() {
		after.setTitleColor(new Color(197,216,255));
		this.setBackground(new Color(100,100,100));
		this.addMouseMotionListener(new CloseMouseListener());
		this.addMouseListener(new CloseMouseListener());
		this.setBorder(after);
	}
	
	public class CloseMouseListener implements MouseMotionListener, MouseListener {

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (!close)
				return;
			x = e.getX() - 50;
			y = e.getY() - 50;
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void drawimage(Image i) {
		img = i;
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 15, 15, null);
		if (close)
			g.drawImage(img, x, y, x + 150, y + 150, x, y, x + 100, y + 100, null);

	}
}