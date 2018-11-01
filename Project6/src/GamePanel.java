import java.awt.BasicStroke;
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
	
	private BasicStroke stroke = new BasicStroke(4);
	private Color color = new Color(255, 19, 73);
	public static Game game = new Game();
	private ValidLocation val = new ValidLocation();
	MouseListener ml = new PanelListener();
	MouseMotionListener mml = new PanelListener();
	Point mp;
	public static String myColor;
	public static String Turn = "";
	
	Image img;

	public GamePanel() {
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.setBackground(new Color(220,179,92));
		addinfo();
		repaint();
	}
	
	private void addinfo() {			//add information about exact location for go tile
		for (int i = 40; i <= width - 40; i+=40)
			val.x.add(i);
		for (int j = 40; j <= height - 40; j+=40)
			val.y.add(j);
		//create valid point
		val.createValidpoint();
	}
	
	public Game getgame() {return game;}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawline(g);
		drawstones(g);
		
		if(Turn.equals(myColor))
			drawlaststones(g);
		
		if (myColor.equals("Black"))		//check which stone should be drawn
			img = Stone.black;
		else
			img = Stone.white;
		
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
	
	private void drawlaststones(Graphics g) {
		if (game.getWsp().size() < 2 && game.getBsp().size() < 2)
			return;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		g2.setColor(color);
		
		if (myColor.equals("Black")) {
			g2.drawOval(game.getWsp().get(game.getWsp().size() -1).x - 21, game.getWsp().get(game.getWsp().size() -1).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
			g2.drawOval(game.getWsp().get(game.getWsp().size() -2).x - 21, game.getWsp().get(game.getWsp().size() -2).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
		}
		else if (myColor.equals("White")) {
			g2.drawOval(game.getBsp().get(game.getBsp().size() -1).x - 21, game.getBsp().get(game.getBsp().size() -1).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
			g2.drawOval(game.getBsp().get(game.getBsp().size() -2).x - 21, game.getBsp().get(game.getBsp().size() -2).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
		}
	}
	
	private void drawline(Graphics g) {		//draw grid line of go tile
		for (int i = 40; i <= width - 40; i+=40)
			g.drawLine(i, 40, i, height - 40);
			
		for (int j = 40; j <= height - 40; j+=40)
			g.drawLine(40, j, width - 40, j);
	}
	
	private void putStone(Point a) {
		if (myColor.equals("Black"))
			game.addBlackStone(new Point(a));
		else if(myColor.equals("White")) 
			game.addWhiteStone(new Point(a));
		
		LoginFrame.client.cs.sendmsg("[Location:" + myColor +"]:" + a.x + "," + a.y);
	}
	
	//if the game is over, remove all listener so that any players are not available to play game
	public void gameover() {
		repaint();
		
		this.removeMouseListener(ml);
		this.removeMouseMotionListener(mml);
		
		int answer = JOptionPane.showConfirmDialog(this, "Do yout want new Game?", "ASKING", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			LoginFrame.gf.startbefore(true);
		}
		else
			System.exit(0);
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
			GameFrame.pp.repaint();
			
			if (!myColor.equals(Turn))
				return;
			
			
			
			Point a = val.trimpoint(e.getPoint());	//make mouse position to exact position of grid line
			
			if (a == null)
				return;
			
			if(game.getWsp().contains(a) || game.getBsp().contains(a)) {
				//if any stones are already placed
				JOptionPane.showMessageDialog(null, "You can't put there!", "WARNING", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			putStone(a);
			repaint();
			
			String  color = game.judge(a, myColor);		//check winner
			if (color != null) 						//if there is winner
				LoginFrame.client.cs.sendmsg("[Winner]:" + color);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if (!myColor.equals(Turn))
				return;
			Point p = val.trimpoint(e.getPoint());
			mp = p;
			repaint();
		}
	}

	
}
