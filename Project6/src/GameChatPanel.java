import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GameChatPanel extends JPanel{
	private JTextArea area = new JTextArea();
	private JTextField field = new JTextField();
	private JButton enter = new JButton("Enter");
	
	public GameChatPanel() {
		setcomponent();
		this.setLayout(null);
		this.setBounds(55, 360, 270, 410);
		this.setBackground(new Color(16, 55, 74));
		this.add(area);
		this.add(field);
		this.add(enter);
	}
	
	public JTextArea getarea() {return area;}
	
	public void setcomponent() {
		area.setBounds(10, 10, 250, 360);
		area.setEditable(false);
		
		field.setBounds(10, 375, 180, 30);
		field.addActionListener(new sendListener());
		enter.setBounds(190, 375, 70, 30);
		enter.setBackground(new Color(255, 236, 66));
		enter.addActionListener(new sendListener());
		enter.setFont(new Font("Arial", Font.BOLD, 13));
	}
	
	private class sendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println(LoginFrame.p1.getitf().getText());
			LoginFrame.client.cs.sendmsg("[" + LoginFrame.p1.getitf().getText() + "]" + field.getText());
			field.setText("");
		}
	}
}
