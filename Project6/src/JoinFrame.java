import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JoinFrame extends JFrame{
	private DBconnect db = null;
	JPanel info = new JPanel();
	
	JLabel id = new JLabel("ID: ");
	JLabel pw = new JLabel("PW: ");
	JLabel pwc = new JLabel("PW check:");
	JLabel name = new JLabel("Name: ");
	JLabel age = new JLabel("Age: ");
	JLabel []label = {id, pw, pwc, name, age};
	
	JTextField itf = new JTextField();
	JPasswordField ptf = new JPasswordField();
	JPasswordField pctf = new JPasswordField();
	JTextField ntf = new JTextField();
	JTextField atf = new JTextField();
	JTextField []tf = {itf, ptf, pctf, ntf, atf};
	
	JButton ibtn = new JButton("Confirm");
	JButton rbtn = new JButton("Register");
	JButton cbtn = new JButton("Cancel");
	
	Font sfont = new Font("¸¼Àº°íµñ", Font.BOLD, 15);
	boolean isconfirmed = false;
	
	public void setlabel() {
		for (JLabel l : label) {
			l.setFont(sfont);
			l.setSize(100, 30);
		}
	}
	
	public void settextfield() {
		for (JTextField f : tf) {
			f.setSize(150, 30);
		}
	}
	
	public void setpanel() {
		info.setLayout(new GridLayout(5, 2));
		for (int i = 0; i < label.length; i++) {
			info.add(label[i]);
			info.add(tf[i]);
		}
		info.setSize(200, 150);
		info.setLocation(35, this.getHeight()/3 - 65);
	}

	public void setbutton() {
		ibtn.setBounds(255, 35, 90, 25);
		ibtn.setFont(sfont);
		ibtn.addActionListener(new idcheck());
		rbtn.setBounds(50, 200, 110, 25);
		rbtn.setFont(sfont);
		rbtn.addActionListener(new register());
		cbtn.setBounds(180, 200, 110, 25);
		cbtn.setFont(sfont);
		cbtn.addActionListener(new cancel());
	}
	
	public void setframe() {
		this.setLayout(null);
		this.setBounds(GameFrame.Width/2 + 200, GameFrame.Height/4 - 70, 400, 300);
		this.add(info);
		this.add(ibtn);
		this.add(rbtn);
		this.add(cbtn);
	}
	
	public JoinFrame(DBconnect DB) {
		super("Join");
		setlabel();
		settextfield();
		setbutton();
		setframe();
		setpanel();
		this.db = DB;
		this.setVisible(true);
	}
	
	private void erase() {
		this.setVisible(false);
		this.dispose();
	}

	public class cancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			erase();
		}
	}
	
	public class register implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(!isconfirmed)
				JOptionPane.showMessageDialog(JoinFrame.this, "Confirm your id!", "WARNING", JOptionPane.WARNING_MESSAGE);
			else if(!String.valueOf(ptf.getPassword()).equals(String.valueOf(pctf.getPassword())))
				JOptionPane.showMessageDialog(JoinFrame.this, "check your pw!", "WARNING", JOptionPane.WARNING_MESSAGE);
			else if(String.valueOf(ptf.getPassword()).trim().equals("") || String.valueOf(pctf.getPassword()).trim().equals("") || ntf.getText().trim().equals(""))
				JOptionPane.showMessageDialog(JoinFrame.this, "Please type your information", "WARNING", JOptionPane.WARNING_MESSAGE);
			else {
				db.register(itf.getText(), String.valueOf(ptf.getPassword()), ntf.getText(), atf.getText());
				JOptionPane.showMessageDialog(JoinFrame.this, "Welcome to Join", "Join Successed",JOptionPane.INFORMATION_MESSAGE);
				for (JTextField t : tf)
					t.setText(null);
				erase();
			}
		}
	}
	
	public class idcheck implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (itf.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(JoinFrame.this, "Type your id!", "WARNING", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(db.idcheck(itf.getText())) {
				JOptionPane.showMessageDialog(JoinFrame.this, "You can use it", "ID Confirmed", JOptionPane.DEFAULT_OPTION);
				isconfirmed = true;
			}
			else {
				JOptionPane.showMessageDialog(JoinFrame.this, "Already Used\n Type another ID", "WARNING", JOptionPane.WARNING_MESSAGE);
			}	
		}
	}

}
