import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame{
	private static MyDB db = new MyDB();
	JoinFrame jf = new JoinFrame(db);
	
	JPopupMenu popup = new JPopupMenu();
	JMenuItem delete = new JMenuItem("delete");
	
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private JButton login = new JButton("Login");
	private JButton join = new JButton("Join");
	private JLabel id = new JLabel("ID:  ");
	private JLabel pw = new JLabel("Password:  ");
	private JTextField Itf = new JTextField();
	private JPasswordField Ptf = new JPasswordField();
	
	private String u_id = null;
	
	private JLabel welcome = new JLabel("Welcome");
	private JPanel getinfo = new JPanel();
	private JPanel beforelogpanel = new JPanel();
	
	private JPanel loginpanel = new JPanel();
	
	private JButton modify = new JButton("Modify");
	private JButton logout = new JButton("Log out");
	private JButton signout = new JButton("Sign out");
	
	private JButton logout1 = new JButton("Log out");;
	private JLabel uid = new JLabel("ID: ");
	private JLabel upw = new JLabel("PW: ");
	private JLabel name = new JLabel("Name: ");
	private JLabel age = new JLabel("Age: ");
	private JLabel []label = {uid, upw, name, age};
	
	private JTextField itf = new JTextField();
	private JTextField ptf = new JTextField();
	private JTextField ntf = new JTextField();
	private JTextField atf = new JTextField();
	private JTextField []tf = {itf, ptf, ntf, atf};
	
	private JPanel info = new JPanel();
	
	JTable list = new JTable();
	private JPanel adminpanel = new JPanel();
	
	boolean visible = true;
	Font sfont = new Font("¸¼Àº°íµñ", Font.BOLD, 15);
	
	private void setuserinfo() {
		for (JLabel l : label) {
			l.setFont(sfont);
			l.setSize(100, 30);
		}
		for (JTextField f : tf) {
			f.setSize(150, 30);
		}
		info.setLayout(new GridLayout(5, 2));
		for (int i = 0; i < label.length; i++) {
			info.add(label[i]);
			info.add(tf[i]);
		}
		info.setSize(200, 150);
		info.setLocation(100, this.getHeight()/3 - 65);
	}
	
	private void setloginpanel() {
		loginpanel.setLayout(null);
		modify.setBounds(this.getWidth()/2 - 130, this.getHeight() - 100, 110, 40);
		modify.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 20));
		modify.addActionListener(new modifyAction());
		modify.setBorderPainted(false);
		logout.setBounds(modify.getX() + modify.getWidth()/2, modify.getY() - 50, 120, 40);
		logout.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 20));
		logout.addActionListener(new logoutAction());
		logout.setBorderPainted(false);
		signout.setBounds(modify.getX() + modify.getWidth() + 10, this.getHeight() - 100, 130, 40);
		signout.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 20));
		signout.addActionListener(new signoutAction());
		signout.setBorderPainted(false);
		setuserinfo();
		loginpanel.add(modify);
		loginpanel.add(signout);
		loginpanel.add(logout);
		loginpanel.add(info);
	}

	private void setadminpanel() {
		adminpanel.setLayout(null);
		MyDB db = new MyDB();
		ArrayList<String []> userinfo = db.getdata();
		int n = userinfo.size();
		String [][]users = new String[n][4];
		String header[] = {"ID", "PW", "NAME", "AGE"};
		JTable list1 = new JTable(users, header);
		list1.addMouseListener(new mouselistener());
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++)
				list1.setValueAt(userinfo.get(i)[j], i, j);
		}
		list = list1;
		list1.setBounds(20, 20, this.getWidth() - 55, this.getHeight()/2);
		logout1.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 20));
		logout1.setBorderPainted(false);
		logout1.setBounds(list1.getX() + list1.getWidth()/2 - 60, list1.getY() + list1.getHeight() + 50,120, 40);
		adminpanel.setBounds(20, 20, this.getWidth() - 55, this.getHeight() - 40);
		adminpanel.add(list1);
		adminpanel.add(logout1);
	}
	
	private void setlabel() {
		welcome.setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 50));
		welcome.setForeground(Color.black);
		welcome.setSize(250, 50);
		welcome.setLocation(this.getWidth()/2 - 125, this.getHeight()/5);
		id.setHorizontalAlignment(SwingConstants.TRAILING);
		pw.setHorizontalAlignment(SwingConstants.TRAILING);
		delete.addActionListener(new menulistener());
		popup.add(delete);
	}
	
	public void setpanel() {
		this.setContentPane(beforelogpanel);
		beforelogpanel.setLayout(null);
		beforelogpanel.add(welcome);
		
		getinfo.setLayout(new GridLayout(2,2));
		getinfo.add(id);
		getinfo.add(Itf);
		getinfo.add(pw);
		getinfo.add(Ptf);
		getinfo.setSize(this.getWidth()/2, 50);
		getinfo.setLocation(this.getWidth()/2  - getinfo.getWidth()/2 - 50, welcome.getY() + welcome.getHeight());
		
		beforelogpanel.add(getinfo);		
		beforelogpanel.add(login);
		beforelogpanel.add(join);
	}
	
	public void setframe() {
		this.setBounds(Width/3, Height/4, 450, 650);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setbutton() {
		login.setBounds(getinfo.getX() + 35, getinfo.getY() + getinfo.getHeight() + 10, welcome.getWidth()/2 - 10, welcome.getHeight()/2);
		join.setBounds(login.getX() + login.getWidth() + 10, login.getY(), login.getWidth(), login.getHeight());
		login.addActionListener(new loginAction());
		join.addActionListener(new joinAction());
		login.setBorderPainted(false);
		join.setBorderPainted(false);
		logout1.addActionListener(new logoutAction());
	}
	
	public class menulistener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int answer = JOptionPane.showConfirmDialog(GUI.this, "Are you sure to delete this person's information", "Asking", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.OK_OPTION) {
				String msg = JOptionPane.showInputDialog(GUI.this, "Type Administrator's password", "Asking", JOptionPane.YES_NO_OPTION);
			if (!msg.equals("1234")) {
				JOptionPane.showMessageDialog(GUI.this, "Password error", " ", JOptionPane.WARNING_MESSAGE);
			}
			else {
				db.remove((String)list.getModel().getValueAt(list.getSelectedRow(), 0));
				adminpanel.removeAll();
				setadminpanel();
				GUI.this.setContentPane(adminpanel);
			}
			}
		}
	}
	
	public class mouselistener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == MouseEvent.BUTTON3) {
				popup.show(GUI.this, e.getX() + 20, e.getY() + 40);
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	public class logoutAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int answer = JOptionPane.showConfirmDialog(GUI.this, "Are you sure to logout?", "Asking", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.OK_OPTION) {
				changeframe(beforelogpanel);
			}
		}
	}
	
	public class modifyAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int answer = JOptionPane.showConfirmDialog(GUI.this, "Are you sure to update your information?", "Asking", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.OK_OPTION) {
				db.modify(u_id, itf.getText(), ptf.getText(), ntf.getText(), atf.getText());
				JOptionPane.showMessageDialog(GUI.this, "Successed", "Successed",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public class signoutAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int answer = JOptionPane.showConfirmDialog(GUI.this, "Are you sure to sign out?", "Asking", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.OK_OPTION) {
				String msg = JOptionPane.showInputDialog(GUI.this, "Type your password", "Asking", JOptionPane.YES_NO_OPTION);
				if (!msg.equals(db.getpw(u_id))) {
					db.remove(u_id);
					changeframe(beforelogpanel);
				}
		}
	}
	}
	
	public class joinAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jf.setvisible(true);				
		}
	}
	
	public class loginAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(db.check(Itf.getText(), String.valueOf(Ptf.getPassword()))) {
				u_id = Itf.getText();
				JOptionPane.showMessageDialog(GUI.this, "Welcome", "Login Successed",JOptionPane.INFORMATION_MESSAGE);
				String []infolist = db.get(u_id);
				itf.setText(infolist[0]);
				ptf.setText(infolist[1]);
				ntf.setText(infolist[2]);
				atf.setText(infolist[3]);
				if (u_id.equals("admin")) {
					setadminpanel();
					changeframe(adminpanel);
				}
				else
					changeframe(loginpanel);
			}
			else
				JOptionPane.showMessageDialog(GUI.this, "Wrong id or password", "WARNING", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void changeframe (JPanel current) {
		System.out.println("!!!");
		JPanel content = (JPanel)getContentPane();
		content.removeAll();
		if (current.equals(loginpanel)) {
			setframe();
			Itf.setText(null);
			Ptf.setText(null);
			setpanel();
		}
		if (current.equals(beforelogpanel)) {
			setpanel();
			Itf.setText(null);
			Ptf.setText(null);
		}		this.setContentPane(current);
		revalidate();
		repaint();
	}
	
	public GUI() {
		setframe();
		setlabel();
		setpanel();
		setbutton();
		setloginpanel();
	}

	public static void main(String[] args) {
		 //TODO Auto-generated method stub
		new GUI();
	}
}
