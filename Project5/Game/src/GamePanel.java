import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	int width = 800, height = 800;
	
	Game game = new Game();
	ValidLocation val = new ValidLocation();
	MouseListener ml = new PanelListener();
	MouseMotionListener mml = new PanelListener();
	Point mp;
	public static String myColor;
	
	public static int isBlack = 0;
	Image img;

	public GamePanel() {
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.setBackground(new Color(220,179,92));
		addinfo();
		repaint();
		this.addMouseListener(ml);
		this.addMouseMotionListener(mml);
	}
	
	public void addinfo() {			//add information about exact location for go tile
		for (int i = 40; i <= width - 40; i+=40)
			val.x.add(i);
		for (int j = 40; j <= height - 40; j+=40)
			val.y.add(j);
		//create valid point
		val.createValidpoint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawline(g);
		drawstones(g);
		
		if (isBlack%4 <= 1)		//check which stone should be drawn
			img = Stone.white;
		else
			img = Stone.black;
		if (mp == null)
			return;
		
		g.drawImage(img, mp.x - 20, mp.y - 20, null);
	}
	
	private void drawstones(Graphics g) {	//draw stones of go tile which already placed
		for (Point p : game.getBsp())
			g.drawImage(Stone.black, p.x - 20, p.y - 20, null);
		for (Point p : game.getWsp())
			g.drawImage(Stone.white, p.x - 20, p.y - 20, null);
	}
	
	private void drawline(Graphics g) {		//draw grid line of go tile
		for (int i = 40; i <= width - 40; i+=40)
			g.drawLine(i, 40, i, height - 40);
			
		for (int j = 40; j <= height - 40; j+=40)
			g.drawLine(40, j, width - 40, j);
	}
	
	public class PanelListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Point a = val.trimpoint(e.getPoint());	//make mouse position to exact position of grid line
			
			if (a == null)
				return;
			
			if(game.getWsp().contains(a) || game.getBsp().contains(a)) {
				//if any stones are already placed
				JOptionPane.showMessageDialog(null, "You can't put there!", "WARNING", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			if (isBlack%4 <= 1) {
				game.addWhiteStone(new Point(a));
				//LoginFrame.client.cs.sendmsg(msg);
			}
			else {
				game.addBlackStone(new Point(a));
				
			}
				
			repaint();
			//repaint to show who's turn is
			GameFrame.pp.repaint();
			
			String  color = game.judge(a, ++isBlack);
			if (color == null)	//if there is no winner
				return;
			else {				//else
				PlayerPanel.winner.showwinner(color);
				gameover();
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point p = val.trimpoint(e.getPoint());
			mp = p;
			repaint();
		}
	}

	//if the game is over, remove all listener so that any players are not available to play game
	public void gameover() {
		isBlack--;
		repaint();
		this.removeMouseListener(ml);
		this.removeMouseMotionListener(mml);
		//LoginFrame.gf.regame();
	}
}
