import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GameUndo extends JButton implements ActionListener {
	private Font font = new Font("Arial", Font.BOLD, 20);
	
	public GameUndo() {
		super("Undo Request");
		this.setBounds(90, 230, 170, 30);
		this.setBorderPainted(false);
		this.setBackground(Color.white);
		this.setFont(font);
		this.addActionListener(this);
	}
	
	public void setenable(boolean enable) {
		this.setEnabled(enable);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		
		int answer = JOptionPane.showConfirmDialog(this, "Do yout really want take back your stone?", "ASKING", JOptionPane.YES_NO_OPTION);		
		if (answer == JOptionPane.YES_OPTION)
			LoginFrame.client.cs.sendmsg("[Undo]:" + GamePanel.myColor);
	}
}
