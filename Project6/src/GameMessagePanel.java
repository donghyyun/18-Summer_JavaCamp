import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameMessagePanel extends JPanel{
	private GameStartButton gsb = new GameStartButton();
	private GameReadyLabel grl = new GameReadyLabel();
	
	public GameMessagePanel() {
		this.setLayout(null);
		this.setBounds(430, 350, 365, 120);
		this.setBackground(new Color(84,74,68));
		this.add(grl);
	}
	
	public void addbutton() {
		this.setSize(365, 160);
		this.add(gsb);
	}
}
