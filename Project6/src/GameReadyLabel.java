import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GameReadyLabel extends JLabel{
	private Font font = new Font("Arial", Font.BOLD, 100);
	
	
	public GameReadyLabel() {
		super("READY");
		this.setForeground(Color.white);
		this.setFont(font);
		this.setBounds(5, 0, 350, 100);
	}
}
