import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

//the game frame
public class GameFrame extends JFrame{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	Stone st = new Stone();
	GamePanel gp = new GamePanel();
	public static PlayerPanel pp = new PlayerPanel();
	
	public GameFrame() {
		super("Connect SIX");
		setframe();
		this.add(gp);
		this.add(pp);
	}
	
	public void gamestart() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setframe() {
		this.setLayout(null);
		this.setBounds(Width/5, Height/8, Width*3/5 + 14, Height*3/4 + 30);
	}
	
	/*public void regame() {
		JPanel content = (JPanel)this.getContentPane();
		content.removeAll();
		revalidate();
	}*/
}