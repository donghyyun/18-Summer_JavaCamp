import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//the panel that contain id, pw and each textfield
public class LoginInfoPanel extends JPanel{
	private JLabel id = new JLabel("ID:  ");
	private JLabel pw = new JLabel("Password:  ");
	private JTextField Itf = new JTextField();
	private JPasswordField Ptf = new JPasswordField();
	
	public LoginInfoPanel() {
		this.setBounds(40, 170, 300, 60);
		setlabel();
		this.setLayout(new GridLayout(2,2));
		this.add(id);
		this.add(Itf);
		this.add(pw);
		this.add(Ptf);
	}
	
	public void setlabel() {
		id.setFont(new Font("Arial", Font.BOLD, 20));
		pw.setFont(new Font("Arial", Font.BOLD, 20));
		id.setSize(30, 20);
		pw.setSize(30, 20);
		
		Itf.setSize(270, 20);
		Ptf.setSize(270, 20);
	}
	
	public JTextField getitf() {return Itf;}
	public JPasswordField getptf() {return Ptf;}
}
