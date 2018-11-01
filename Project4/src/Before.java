import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Before extends JPanel {	
	private Image img;
	private TitledBorder before = new TitledBorder(new LineBorder(new Color(221,0,233)), "Before");
	private JLabel label= new JLabel("Clik here to add img file!");
	private Font font = new Font("Arial", Font.BOLD, 15);
	
	public Before() {
		this.setLayout(null);
		this.setBounds(30, 30, Main.Width/4 + 80, Main.Height*3/5 + 30);
		label.setFont(font);
		label.setForeground(Color.white);
		label.setBounds(this.getWidth()/2 - 80, this.getHeight()/2 - 20, 200, 40);
		before.setTitleColor(new Color(221,0,233));
		this.add(label);
		this.setBorder(before);
		this.setBackground(new Color(100,100,100));
	}	
	
	public void drawimage(Image i){
		img = i;
		repaint();
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 15, 15, null);
	}
}
