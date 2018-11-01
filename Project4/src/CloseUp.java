import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class CloseUp {
	
	public JButton closeup = new JButton("CloseUp");
	
	private Font font = new Font("Arial", Font.BOLD, 15);
	
	BufferedImage bimg;
	Image img;
	
	int x = 0;
	int y = 0;
	public CloseUp() {
		closeup.setBorderPainted(false);
		closeup.setBackground(new Color(30,30,30));
		closeup.setForeground(Color.white);
		closeup.setFont(font);
		closeup.setBounds(480, Main.Height*4/5 - 100, 120, 40);
		closeup.addMouseListener(new CloseListener());
		
	}

	public class CloseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			After.close = !After.close;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	
	
}


