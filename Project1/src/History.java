import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class History extends JFrame{
	Stack<String> st = new Stack<String>();
	JButton[] hs = new JButton[5];
	private int index = 0;

	public History() {
		super("History");
		setpage();
		setcontents();
	}
	
	public void setpage() {
		this.setBounds(1120, 100, 300, 500);
		this.setLayout(new GridLayout(5,0));
	}
	
	public void setcontents() {
		for (int i = 0; i < 5; i++) {
			hs[i] = new JButton();
			hs[i].setSize(250, 100);
			hs[i].setFont(new Font("¸¼Àº°íµñ", Font.BOLD, 30));
			hs[i].setBackground(Color.WHITE);
			this.add(hs[i]);
		}
	}
	
	
	public void save(String result) {
		st.push(result);
		index++;
		System.out.println(result + " is saved.");
	}

	public void load() {
		for (JButton btn : hs) {
			if (index > 0)
				btn.setText(st.elementAt(--index));
			else
				break;
		}
		index = st.size();
	}
	
	public void showhistory() {
		load();
		this.setVisible(true);
	}
}