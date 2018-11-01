import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameStartButton extends JButton{
	private Font font = new Font("Arial", Font.BOLD, 30);
	
	public GameStartButton() {
		super("START!");
		this.setForeground(Color.white);
		this.setFont(font);
		this.setBorderPainted(false);
		this.setBackground(new Color(255, 235, 51));
		this.setBounds(200, 110, 145, 40);
		this.addActionListener(new StartButtonListener());
	}
	
	private class StartButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoginFrame.client.cs.sendmsg("[Start]:");
		}
		
	}
}
