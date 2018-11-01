import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//the frame that use for login
public class LoginFrame extends JFrame{
	private JLabel welcome = new JLabel("Connect SIX");
	public LoginInfoPanel p1 = new LoginInfoPanel();
	public static JButton login = new JButton("Log in");
	private JButton join = new JButton("Join");
	
	public static GameFrame gf = new GameFrame();
	
	Font font = new Font("Arial", Font.BOLD, 20);
	Color c = new Color(255,255,255);
	JPanel buttons = new JPanel();
	
	private DBconnect db = new DBconnect();
	
	public LoginFrame() {
		setframe();
		setlabel();
		setbuttons();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setframe() {
		this.setLocationRelativeTo(null);
		this.setLocation(this.getLocation().x - 400, this.getLocation().y - 300);
		this.setSize(400, 500);
		this.setLayout(null);
		this.add(welcome);
		this.add(p1);
		this.add(buttons);
	}
	
	public void setlabel() {
		welcome.setFont(new Font("Arial", Font.BOLD, 40));
		welcome.setBounds(80, 20 ,232, 100);
	}
	
	public void setbuttons() {
		buttons.setBounds(70, 300, 260, 50);
		buttons.setLayout(null);
		
		login.setBorderPainted(false);
		login.setBackground(c);
		login.setFont(font);
		login.setBounds(20, 5, 100, 40);
		login.addActionListener(new loginListener());
		
		join.setBorderPainted(false);
		join.setBackground(c);
		join.setFont(font);
		join.setBounds(140, 5, 100, 40);
		join.addActionListener(new joinListener());
		
		buttons.add(login);
		buttons.add(join);
	}
	
	public void startGame() {
		//client  = new Client(this.p1.getitf().getText());
		this.setVisible(false);
		this.dispose();
		gf.gamestart();
	}
	
	private class loginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (db.check(p1.getitf().getText(), String.valueOf(p1.getptf().getPassword())))
				startGame();
			else
				JOptionPane.showMessageDialog(LoginFrame.this, "Confirm Black's id or password!", "WARNING", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private class joinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new JoinFrame(db);
		}
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginFrame();
	}
}
