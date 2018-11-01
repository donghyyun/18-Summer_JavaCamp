import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame{
	Calculate cal = new Calculate();
	History hs = new History();
	
	private JLabel answer = new JLabel();				//���� �����ִ� answer text
	private JPanel button_panel = new JPanel();			//button���� ��ġ�� ����(Panel)

	private String expression = null;					//���� ��
	private String recent = null;						//���� �ֱٿ� �Էµ� ��
	
	private JButton all_clear = new JButton("AC");		//��ü ���� clear_error��Ŵ
	private JButton clear_error = new JButton("CE");	//���� ���� �ִ� ���� clear_error
	
	private JButton divide = new JButton("��");			//������
	private JButton times = new JButton("��");			//���ϱ�
	private JButton subtract = new JButton("-");		//����
	private JButton add = new JButton("+");				//���ϱ�
	private JButton mod = new JButton("%");				//modulate
	
	private JButton fact = new JButton("n!");			//factorial
	private JButton equal = new JButton("=");			//'='
	
	private JButton history = new JButton("H");			//history
	
	private JButton[] operators = {all_clear, clear_error, divide, times, history,
											fact, subtract, add, mod, equal};
	
	private JButton comma = new JButton(".");			//�Ҽ���
	
	private JButton nine = new JButton("9");
	private JButton eight = new JButton("8");
	private JButton seven = new JButton("7");
	private JButton six = new JButton("6");
	private JButton five = new JButton("5");
	private JButton four = new JButton("4");
	private JButton three = new JButton("3");
	private JButton two = new JButton("2");
	private JButton one = new JButton("1");
	private JButton zero = new JButton("0");
		
	private JButton[] operands = {comma, nine, eight, seven, six, five, four, three,
											two, one, zero};
	
	private JButton[] buttons = {history, all_clear, clear_error, mod, divide,
			seven, eight, nine, times,
			four, five, six, subtract,
			one, two, three, add,
			fact, zero, comma, equal};
	
	boolean flag = false;
	
	public Calculator() {
		super("Calculator");
		setframe();
		setlabel();
		setbuttons();
		setVisible(true);
	}

	
	private void setframe() {
		setBounds(700, 100, 420, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setlabel() {
		answer.setOpaque(true);
		answer.setBackground(Color.lightGray);
		answer.setBounds(20, 20, 365, 90);
		answer.setHorizontalAlignment(SwingConstants.TRAILING);
		answer.setFont(new Font("�������", Font.BOLD, 50));
		add(answer);
	}
	
	private void setbuttons() {
		button_panel.setBounds(0, 180, 404, 382);
		button_panel.setLayout(new GridLayout(5,4));
		Font button_font = new Font("�������", Font.BOLD, 30);
		setlistener();
		
		for (JButton btn : buttons) {
			btn.setBackground(new Color(245,245,245));
			btn.setFont(button_font);
			if (btn.getText().equals("H"))
				btn.setBounds(300, 120, 85, 50);
			else
				button_panel.add(btn);
		}
		add(history);
		add(button_panel);
	}
	
	public void setlistener() {
		for (JButton operator : operators)
			operator.addActionListener(new OperatorListener());
		
		for (JButton operand : operands)
			operand.addActionListener(new OperandListener());
	}
	
	
	private class OperandListener implements ActionListener {
		//@Override
		public void actionPerformed(ActionEvent e) {
			operand_update(e.getSource());
		}
	};
	
	private class OperatorListener implements ActionListener {
		//@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == history) {
				hs.showhistory();
				return;
			}
			operator_update(e.getSource());
		}
	};

	public void operator_update (Object btn) {
		if (expression == null || flag)
			return;
		
		JButton cliked = (JButton)btn;
		switch (cliked.getText()) {
		case "AC" :
			expression = null;
			break;
		case "CE" : 
			if (expression.length() <= 1) {
				expression = null; 
				break;
			}
			expression = expression.split(recent)[0];
			recent = expression.substring(expression.length()-1);
			break;
		case "=" : 
			if(expression.endsWith(" "))
				return;
			String result = null;
			result = do_calculate(expression);
			if(result == null)
				return;
			
			hs.save(result);
			expression = result;
			flag = true;
			break;
		case "n!" :
			if(expression.endsWith(" "))
				return;
			if (!expression.contains(" "))
				expression = String.valueOf(factorial(Integer.valueOf(expression)));
			else
				expression = expression.replace(expression.split(" ")[2], String.valueOf(factorial(Integer.valueOf(expression.split(" ")[2]))));
			break;
		default :			//+, -, *, /, %
			if(expression.endsWith(" "))
				return;
			if(expression.contains(" "))
				expression = do_calculate(expression);
			
			recent = cliked.getText();
			expression = expression + " " + recent + " ";
		}
		answer.setText(expression);
	}

	public void operand_update (Object btn) {
		JButton cliked = (JButton)btn;
		
		recent = cliked.getText();
		
		if (expression == null || expression.equals("0") || flag) {
			if (recent.equals("."))
				recent = "0.";
			flag = false;
			expression = recent;
		}
		else if (expression.lastIndexOf(" ") == expression.length()-1 && recent.equals(".")) {
			recent = "0.";
			expression += recent;
		}
		else
			expression += recent;
		answer.setText(expression);
	}	
	public int factorial(int n) {return n <= 1? 1 : n*factorial(n-1);}
	
	public String do_calculate(String text) {			
		String []mem = text.split(" ");
		String result = null;
		
		if(mem.length < 3)
			return null;

		switch(mem[1]) {
		case "+": result = cal.add(mem[0], mem[2]); break;
		case "-": result = cal.subtract(mem[0], mem[2]); break;
		case "��": result = cal.times(mem[0], mem[2]); break;
		case "��":
			if (mem[2].equals("0")) {
				JOptionPane.showMessageDialog(null, "You can't divide by zero!", "Warning", JOptionPane.WARNING_MESSAGE);
				return null;
			}
			result = cal.divide(mem[0], mem[2]);
			break;
		case "%" :
			if (mem[2].equals("0")) {
				JOptionPane.showMessageDialog(null, "You can't modulate by zero!", "Warning", JOptionPane.WARNING_MESSAGE);
				return null;
			}
				
			result = cal.modulate(mem[0], mem[2]);
			break;
		}
		
		answer.setText(result);
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator calculator = new Calculator();
	}
}
