import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Recovery extends JButton{
private Font font = new Font("Arial", Font.BOLD, 15);
	
	public Recovery() {	
		this.setBorderPainted(false);
		this.setBackground(new Color(30,30,30));
		this.setText("Recovery");
		this.setFont(font);
		this.setForeground(Color.white);
		this.setBounds(330, Main.Height*4/5 - 100, 120, 40);
		this.addActionListener(new RecoveryListener());
	}
	
	public class RecoveryListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Main.aft.drawimage(Main.orgimg);
		}
	}
}
