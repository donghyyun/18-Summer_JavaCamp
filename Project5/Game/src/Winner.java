import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

//winner label(text)
public class Winner extends JLabel{
	private Font font  = new Font("Arial", Font.BOLD, 60);
	public Winner() {
		super("Win");
		this.setBounds(20, 370, 400, 60);
		this.setForeground(Color.white);
		this.setFont(font);
		this.setVisible(false);
	}
	
	//if game is over show who is winner
	public void showwinner(String win) {
		this.setText(win + " " + this.getText());
		if (win.equals("Black"))
			this.setForeground(Color.black);
		this.setVisible(true);
	}
}