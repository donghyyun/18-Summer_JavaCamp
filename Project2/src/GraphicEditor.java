import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

@SuppressWarnings("serial")
public class GraphicEditor extends JFrame{
	//screen size
	private int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	//ArrayList
	ArrayList<Color> colorl = new ArrayList<Color>();
	ArrayList<Float> strokel = new ArrayList<Float>();
	ArrayList<Shape> order = new ArrayList<Shape>();
	Stack<Shape> ostack = new Stack<Shape>();
	Stack<Color> cstack = new Stack<Color>();
	Stack<Float> sstack = new Stack<Float>();
	
	Shape Cshape = null;
	
	ArrayList<Line2D> penorder = new ArrayList<Line2D>();
	ArrayList<Line2D> eraserorder = new ArrayList<Line2D>();
	
	ArrayList<Integer> pindexs = new ArrayList<Integer>();
	ArrayList<Integer> eindexs = new ArrayList<Integer>();
	
	Rectangle2D erase_rect = new Rectangle2D.Double();
	
	//ÁÂÇ¥
	int x1 = 0, x2 = 0;
	int y1 = 0, y2 = 0;
	int mx = 0, my = 0;
	int delta = 20;
	double rx = 0, ry = 0;
	double rw = 0, rh = 0;
	double dx = 0, dy = 0;
	double dx2 = 0, dy2 = 0;
	
	JPopupMenu popup = new JPopupMenu();
	
	JMenuItem copy = new JMenuItem("Copy");
	JMenuItem paste = new JMenuItem("Paste");
	JMenuItem delete = new JMenuItem("Delete");
	
	//Menu
	JMenuBar mb = new JMenuBar();
	
	JMenu drawOption = new JMenu("Draw");
	JMenuItem line = new JMenuItem("Line");
	JMenuItem rectangle = new JMenuItem("Rectangle");
	JMenuItem ellipse = new JMenuItem("Ellipse");
	JMenuItem pen = new JMenuItem("Pen");
	JMenuItem eraser = new JMenuItem("Eraser");
	JMenuItem []drawMenu = {line, rectangle, ellipse, pen, eraser};
	
	JMenu propertyOption = new JMenu("Property");
	
	JMenu stroke = new JMenu("Stroke");
	JMenuItem one = new JMenuItem("1");
	JMenuItem two = new JMenuItem("2");
	JMenuItem three = new JMenuItem("5");
	JMenuItem four = new JMenuItem("8");
	JMenuItem five = new JMenuItem("10");
	JMenuItem six = new JMenuItem("20");
	JMenuItem []strokeMenu = {one, two, three, four, five, six};
	
	JMenuItem color = new JMenuItem("Color");
	JMenuItem []propertyMenu = {stroke, color};
	
	//undo & redo button
	JButton undo = new JButton("Undo");
	JButton redo = new JButton("Redo");
	JButton move = new JButton("Move");
	JButton reset = new JButton("Reset");
	
	//default value
	String dOption = "Line";
	float s = 1;
	boolean isundo = false;
	boolean resizable = false;
	Color c = Color.BLACK;
	Color bc = this.getBackground();
	
	public GraphicEditor () {
		setmenu();
		setframe();
		setbutton();
	}
	
	public void setbutton() {
		ActionListener l = new Button();
		undo.addActionListener(l);
		redo.addActionListener(l);
		move.addActionListener(l);
		reset.addActionListener(l);
		undo.setBounds(5, 5, this.getWidth()/15, this.getHeight()/20);
		redo.setBounds(5, undo.getY() + undo.getHeight() + 5, this.getWidth()/15, this.getHeight()/20);
		move.setBounds(5, redo.getY() + redo.getHeight() + 5, this.getWidth()/15, this.getHeight()/20);
		reset.setBounds(5, move.getY() + move.getHeight() + 5, this.getWidth()/15, this.getHeight()/20);
		this.add(undo);
		this.add(redo);
		this.add(move);
		this.add(reset);
	}
	
	public void setmenu() {
		ActionListener l = new DMenu();
		for (JMenuItem mi : drawMenu) {
			if (mi.getText().equals("Eraser"))
				drawOption.addSeparator();
			drawOption.add(mi);
			mi.addActionListener(l);
		}
		l = new PMenu();
		for (JMenuItem mi : propertyMenu) {
			propertyOption.add(mi);
			mi.addActionListener(l);
		}
		for (JMenuItem mi : strokeMenu) {
			stroke.add(mi);
			mi.addActionListener(l);
		}
		mb.add(drawOption);
		mb.add(propertyOption);
		l = new PopMenu();
		copy.addActionListener(l);
		paste.addActionListener(l);
		delete.addActionListener(l);
		
		popup.add(copy);
		popup.add(paste);
		popup.addSeparator();
		popup.add(delete);
	}
	
	public void setframe() {
		this.setBounds(Width/10, Height/10, Width*3/5, Height*3/5);
		this.addMouseListener(new Draw());
		this.addMouseMotionListener(new Draw());
		this.add(popup);
		this.setJMenuBar(mb);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	public void beforePaint(Graphics2D g2) {
		int i = 0, i1 = 0;
		for (int index = 0; index < order.size(); index++) {
			g2.setColor(colorl.get(index));
			g2.setStroke(new BasicStroke(strokel.get(index)));
			if (penorder.contains(order.get(index))) {
				for(int j = 0 ; j < pindexs.get(i); j++)
					g2.draw(penorder.get(j));
				i++;
			}
			if (eraserorder.contains(order.get(index))) {
				for(int j = 0 ; j < eindexs.get(i1); j++)
					g2.draw(eraserorder.get(j));
				i1++;
			}
			g2.draw(order.get(index));
		}
	}
		
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		super.paint(g2);
		beforePaint(g2);
		
		if(isundo) {
			isundo = false;
			return;
		}
		
		g2.setColor(c);
		g2.setStroke(new BasicStroke(s));
		
		switch(dOption) {
		case "Line":	
			g2.drawLine(x1, y1, x2, y2);
			return;
		case "Rectangle":
			g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
			return;
		case "Ellipse":
			g2.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
			return;
		}
	}
	
	public class DMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dOption = ((JMenuItem)e.getSource()).getText();
			isundo = true;
			repaint();
		}
	}
	
	public class Button implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String btn = ((JButton)e.getSource()).getText();
			if (btn.equals("Move"))
				dOption = "Move";
			else if (btn.equals("Undo")) {
				if(order.isEmpty())
					return;
				ostack.push(order.remove(order.size() - 1));
				sstack.push(strokel.get(strokel.size() - 1));
				cstack.push(colorl.get(colorl.size() - 1));
			}
			else if (btn.equals("Redo")){
				if(ostack.isEmpty())
					return;
				order.add(ostack.pop());
				strokel.add(sstack.pop());
				colorl.add(cstack.pop());
			}
			else if (btn.equals("Reset")) {
				order.clear();
				colorl.clear();
				strokel.clear();
				resizable = false;
				if (dOption.equals("Move"))
					dOption = "Line";
			}
			isundo = true;
			repaint();
		}
	}
	
	public class PopMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(((JMenuItem)e.getSource()).getText()) {
			case "Delete":
				for (Shape shape : order) {
					if(shape.getBounds2D().contains(mx, my)) {
						order.remove(order.indexOf(shape));
						repaint();
						if (order.indexOf(shape) == order.size() - 1)
							isundo = true;
						return;
					}
				}
			case "Copy":
				repaint();
				for (Shape shape : order) {
					if(shape.getBounds2D().contains(mx, my)) {
						colorl.add(colorl.get(order.indexOf(shape)));
						strokel.add(strokel.get(order.indexOf(shape)));
						if (shape instanceof Rectangle2D.Double) {
							Rectangle2D.Double rect = (Rectangle2D.Double) shape;
							Rectangle2D.Double rect2 = (Double) rect.clone();
							Cshape = rect2;
						}
						else if (shape instanceof Line2D.Double) {
							Line2D.Double ln = (Line2D.Double) shape;
							Line2D.Double ln2 = (Line2D.Double) ln.clone();
							Cshape = ln2;
						}
						else if (shape instanceof Ellipse2D.Double) {
							Ellipse2D.Double ell = (Ellipse2D.Double) shape;
							Ellipse2D.Double ell2 = (Ellipse2D.Double) ell.clone();
							order.add(ell2);
							Cshape = ell2;
						}
						repaint();
						return;
					}
				}
			case "Paste":
				if (Cshape == null)
					return;
				order.add(Cshape);
				
				if (Cshape instanceof Rectangle2D.Double) {
					Rectangle2D.Double rect = (Rectangle2D.Double) Cshape;
					rect.setRect(rect.x + delta, rect.y + delta, rect.width, rect.height);
				}
				else if (Cshape instanceof Line2D.Double) {
					Line2D.Double ln = (Line2D.Double) Cshape;
					ln.setLine(ln.x1 + delta, ln.y1 + delta, ln.x2 + delta, ln.y2 + delta);
				}
				else if (Cshape instanceof Ellipse2D.Double) {
					Ellipse2D.Double ell = (Ellipse2D.Double) Cshape;
					ell.setFrame(ell.x + delta, ell.y + delta, ell.width, ell.height);
				}
				isundo = true;
				dOption = "Move";
				repaint();
			}
		}
	}
	
	public class PMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String pOption = ((JMenuItem)e.getSource()).getText();
			switch (pOption) {
			case "Color" :
				c = JColorChooser.showDialog(color, "Color", Color.BLACK);
				break;
			default:
				s = Integer.valueOf(pOption);
			}
			isundo = true;
			repaint();
		}
	}

	public class Draw implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				mx = e.getX();
				my = e.getY();
				popup.show(GraphicEditor.this, e.getX(), e.getY());
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == MouseEvent.BUTTON3) 
				return;
			
			if(resizable && order.get(order.size() - 1).getBounds2D().contains(e.getPoint())) {
				rx = e.getX();
				ry = e.getY();
				if (order.get(order.size() - 1) instanceof Rectangle2D.Double) {
					Rectangle2D.Double rect = (Rectangle2D.Double) order.get(order.size() - 1);
					rw = rect.width;
					rh = rect.height;
				}
				else if (order.get(order.size() - 1) instanceof Line2D.Double) {
					Line2D.Double ln = (Line2D.Double) order.get(order.size() - 1);
					rw = ln.x2;
					rh = ln.y2;
				}
				else if (order.get(order.size() - 1) instanceof Ellipse2D.Double) {
					Ellipse2D.Double ell = (Ellipse2D.Double) order.get(order.size() - 1);
					rw = ell.width;
					rh = ell.height;
				}
				return;
			}
			else
				resizable = false;
			
			x1 = e.getX();
			y1 = e.getY();
			if (dOption.equals("Move")) {
				for (int index = 0; index < order.size(); index++) {
					if(order.get(index).getBounds2D().contains(e.getPoint())){
						if (order.get(index) instanceof Rectangle2D.Double) {
							Rectangle2D.Double rect = (Rectangle2D.Double) order.get(index);
							dx = e.getX() - rect.getX();
							dy = e.getY() - rect.getY();
							break;
						}
						else if (order.get(index) instanceof Line2D.Double) {
							Line2D.Double ln = (Line2D.Double) order.get(index);
							dx = e.getX() - ln.x1;
							dy = e.getY() - ln.y1;
							dx2 = ln.x2 - e.getX();
							dy2 = ln.y2 - e.getY();
							break;
						}
						else if (order.get(index) instanceof Ellipse2D.Double) {
							Ellipse2D.Double ell = (Ellipse2D.Double) order.get(index);
							dx = e.getX() - ell.getX();
							dy = e.getY() - ell.getY();
							break;
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == MouseEvent.BUTTON3) 
				return;
			if (resizable) {
				resizable = false;
				return;
			}
			x2 = e.getX();
			y2 = e.getY();
			Graphics2D g2 = (Graphics2D) getGraphics();
			
			g2.setColor(c);
			g2.setStroke(new BasicStroke(s));
			
			if (!dOption.equals("Eraser"))
				colorl.add(c);
			else
				colorl.add(bc);
			
			strokel.add(s);
			
			switch(dOption) {
			case "Line":
				Line2D li = new Line2D.Double(x1, y1, x2, y2);
				order.add(li);
				g2.draw(li);
				resizable = true;
				break;
			case "Rectangle":
				Rectangle2D rect = new Rectangle2D.Double(Math.min(x1, x2), Math.min(y1,  y2), Math.abs(x2-x1), Math.abs(y2-y1));
				order.add(rect);
				g2.draw(rect);
				resizable = true;
				break;
			case "Ellipse":
				Ellipse2D ell = new Ellipse2D.Double(Math.min(x1, x2), Math.min(y1,  y2), Math.abs(x2-x1), Math.abs(y2-y1));
				order.add(ell);
				g2.draw(ell);
				resizable = true;
				break;
			case "Eraser":
				order.add(eraserorder.get(eraserorder.size()-1));
				eindexs.add(eraserorder.indexOf(order.get(order.size() - 1)));
				break;
			case "Pen":
				order.add(penorder.get(penorder.size()-1));
				pindexs.add(penorder.indexOf(order.get(order.size() - 1)));
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getY() < Height/20)
				return;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			
			if (resizable && order.get(order.size() - 1).getBounds2D().contains(e.getPoint())) {
				if (order.get(order.size() - 1) instanceof Rectangle2D.Double) {
					Rectangle2D.Double rect = (Rectangle2D.Double) order.get(order.size() - 1);
					rect.setRect(rect.x, rect.y, rw + (e.getX() - rx), rh + (e.getY() - ry));
				}
				else if (order.get(order.size() - 1) instanceof Line2D.Double) {
					Line2D.Double ln = (Line2D.Double) order.get(order.size() - 1);
					ln.setLine(ln.x1, ln.y1, rw + (e.getX() - rx), rh + (e.getY() - ry));
				}
				else if (order.get(order.size() - 1) instanceof Ellipse2D.Double) {
					Ellipse2D.Double ell = (Ellipse2D.Double) order.get(order.size() - 1);
					ell.setFrame(ell.x, ell.y, rw + (e.getX() - rx), rh + (e.getY() - ry));
				}
				isundo = true;
				repaint();
				return;
			}
			
			if (dOption.equals("Move")) {
				for (int index = 0; index < order.size(); index++) {
					if(order.get(index).getBounds2D().contains(e.getPoint())){
						if (order.get(index) instanceof Rectangle2D.Double) {
							Rectangle2D.Double rect = (Rectangle2D.Double) order.get(index);
							rect.setRect(e.getX() - dx, e.getY() - dy, rect.width, rect.height);
							break;
						}
						else if (order.get(index) instanceof Line2D.Double) {
							Line2D.Double ln = (Line2D.Double) order.get(index);
							ln.setLine(e.getX() - dx, e.getY() - dy, e.getX() + dx2, e.getY() + dy2);
							break;
						}
						else if (order.get(index) instanceof Ellipse2D.Double) {
							Ellipse2D.Double ell = (Ellipse2D.Double) order.get(index);
							ell.setFrame(e.getX() - dx, e.getY() - dy, ell.width, ell.height);
							break;
						}
					}
				}
				repaint();
				return;
			}
			x2 = e.getX();
			y2 = e.getY();
			
			Line2D li = new Line2D.Double(x1, y1, x2, y2);
			switch (dOption) {
			default : 
				repaint();
				return;
			case "Eraser":
				g2.setColor(bc);
				eraserorder.add(li);
				break;
			case "Pen" :
				g2.setColor(c);
				penorder.add(li);
				break;
			}
			g2.setStroke(new BasicStroke(s));
			
			g2.draw(li);
			x1 = x2;
			y1 = y2;
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GraphicEditor();
	}
}