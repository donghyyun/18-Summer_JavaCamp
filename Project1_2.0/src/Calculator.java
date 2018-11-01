import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame{
	Calculate cal = new Calculate();
	
	private JLabel answer = new JLabel();				//답을 보여주는 answer text
	private JPanel button_panel = new JPanel();

	private String expression = null;
	private String recent = null;
	
	private JButton all_clear = new JButton("AC");	//전체 값을 clear_error시킴
	private JButton clear_error = new JButton("CE");			//현재 쓰고 있는 값만 clear_error
	
	private JButton divide = new JButton("÷");			//나누기
	private JButton times = new JButton("×");			//곱하기
	private JButton subtract = new JButton("-");			//빼기
	private JButton add = new JButton("+");				//더하기
	private JButton mod = new JButton("%");				//modulate
	
	private JButton fact = new JButton("n!");			//부호
	private JButton equal = new JButton("=");			//'='
	
	private JButton[] operators = {all_clear, clear_error, divide, times, 
											fact, subtract, add, mod, equal};
	
	private JButton comma = new JButton(".");			//소수점
	
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
	
	private JButton[] buttons = {all_clear, clear_error, mod, divide,
			seven, eight, nine, times,
			four, five, six, subtract,
			one, two, three, add,
			fact, zero, comma, equal};
	
	
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
		answer.setFont(new Font("맑은고딕", Font.BOLD, 50));
		add(answer);
	}
	
	private void setbuttons() {
		button_panel.setBounds(0, 150, 404, 412);
		button_panel.setLayout(new GridLayout(5,4));
		Font button_font = new Font("맑은고딕", Font.BOLD, 30);
		setlistener();
		
		for (JButton btn : buttons) {
			btn.setBackground(new Color(245,245,245));
			btn.setFont(button_font);
			button_panel.add(btn);
		}
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
			operator_update(e.getSource());
		}
	};
	
	
	public void operator_update (Object btn) {
		boolean flag = false;
		
		if (!(btn instanceof JButton))
			System.exit(0);
		
		if (expression == null)
			return;
		
		for (JButton b : operators) {
			if (flag || b.getText().equals(recent))
				return;
		}
		
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
			break;
		case "=" : 
			String result = null;
			result = do_calculate(expression);
			if(result == null)
				return;
			expression = result;
			flag = true;
			break;
		case "n!" :
			if (!expression.contains(" "))
				expression = String.valueOf(factorial(Integer.valueOf(expression)));
			else
				expression = expression.replace(expression.split(" ")[2], String.valueOf(factorial(Integer.valueOf(expression.split(" ")[2]))));
			break;
		default : 
			recent = cliked.getText();
			expression = expression + " " + recent + " ";
		}
		answer.setText(expression);
	}
	//'=' 계속 누를때
	
	public void operand_update (Object btn) {
		if (!(btn instanceof JButton))
			System.exit(0);
		JButton cliked = (JButton)btn;
		
		recent = cliked.getText();
		
		if (expression == null) {
			if (recent.equals("."))
				recent = "0.";
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
		case "×": result = cal.times(mem[0], mem[2]); break;
		case "÷":
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
