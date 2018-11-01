import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

//the game frame
public class GameFrame extends JFrame{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	Stone st = new Stone();		//get stone image
	private GamePanel gp = new GamePanel();
	public static PlayerPanel pp = new PlayerPanel();
	private GameMessagePanel gmp = new GameMessagePanel();
	
	public GameFrame() {
		super("Connect SIX");
		setframe();
		this.add(gmp);
		this.add(gp);
		this.add(pp);
	}
	
	public void gameready() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//서버에서 2명 접속하면 startbefore 호출하고, 스타트 버튼 누르면 시작하도록
	public void startbefore(boolean regame) {
		gp.getgame().getBsp().clear();
		gp.getgame().getWsp().clear();
		
		gmp.setVisible(true);
		gmp.addbutton();
		
		if (regame) {
			regame();
		}
	}
	
	public void gamestart() {
		gmp.setVisible(false);
		gp.getgame().getBsp().add((new Point(400,400)));
		
		gp.addMouseListener(gp.ml);
		gp.addMouseMotionListener(gp.mml);
		
		gp.repaint();
		gp.Turn = "White";
		pp.repaint();
	}
	
	public void setframe() {
		this.setLayout(null);
		this.setBounds(Width/5, Height/8, Width*3/5 + 14, Height*3/4 + 30);
	}
	
	public GamePanel getgamepanel() {return gp;}
	
	public void regame() {
		gp.getgame().getBsp().add((new Point(400,400)));
		gp.mp = null;
		
		gp.Turn = "White";
		pp.getwinner().setVisible(false);
		
		gp.removeMouseListener(gp.ml);
		gp.removeMouseMotionListener(gp.mml);
		LoginFrame.client.cs.sendmsg("[Initialize turn]:");
	}
}