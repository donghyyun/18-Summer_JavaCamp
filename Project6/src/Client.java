import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	String name;
	String color;
	GamePanel gp;
	ClientSender cs;
	ClientReceiver cr;
	
	public Client(String name, GamePanel gp) {
		this.name = name;
		this.gp = gp;
		connect();
	}
	
	public void connect() {
		try {
			String serverIp = "127.0.0.1";
			Socket socket = new Socket(serverIp, 9909);
			
			cs = new ClientSender(socket, name);
			cr = new ClientReceiver(socket, gp);
			System.out.println("서버에 연결되었습니다. name: " + name);
			Thread sender = new Thread(cs);
			Thread receiver = new Thread(cr);
			
			sender.start();
			receiver.start();
		} catch(ConnectException ce) {
			ce.printStackTrace();
		} catch(Exception e) {}
	}
	
	public static class ClientSender extends Thread {
		Socket socket;
		DataOutputStream out;
		String name;
		
		ClientSender(Socket socket, String name) {
			this.socket = socket;
			this.name = name;
			try {
				out = new DataOutputStream(socket.getOutputStream());
			} catch(Exception e) {}
		}
		
		public void run() {
			//client가 send할 정보
			try {
				if(out!=null) {
					out.writeUTF(name);
				}
			} catch(IOException e) {}
		}
		
		public void sendmsg(String msg) {
			try {
				out.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		String state;
		GamePanel gp;
		
		ClientReceiver(Socket socket, GamePanel gp) {
			this.socket = socket;
			this.gp = gp;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch(IOException e) {}
		}
		
		public void run() {
			while(in!=null) {
				try {
					state = in.readUTF();
					
					if (state.contains("[Ready]:")) {
						LoginFrame.gf.startbefore(false);
						continue;
					}
					if (state.contains("[Start]:")) {
						LoginFrame.gf.gamestart();
						continue;
					}
					
					if (state.contains("[Undo]:")) {
						state = state.substring(state.indexOf(":") + 1);
						if(state.equals("Black"))
							gp.getgame().getBsp().remove(gp.getgame().getBsp().size() - 1);
						else if(state.equals("White"))
							gp.getgame().getWsp().remove(gp.getgame().getWsp().size() - 1);
						
						gp.repaint();
						continue;
					}
					
					if (state.contains("[Color]:")) {
						if (GamePanel.myColor  == null) {
							state = state.substring(state.indexOf(":") + 1);
							GamePanel.myColor = state;
							System.out.println("User Color: " + GamePanel.myColor);
						}
					}
					else if (state.contains("[Location:Black]:") && GamePanel.myColor.equals("White")) {
						gp.getgame().getBsp().add(new Point(Integer.valueOf(state.substring(state.lastIndexOf(":") + 1, state.indexOf(",")))
														, Integer.valueOf(state.substring(state.indexOf(",") + 1))));
						gp.repaint();
					}
					else if (state.contains("[Location:White]:") && GamePanel.myColor.equals("Black")) {
						gp.getgame().getWsp().add(new Point(Integer.valueOf(state.substring(state.lastIndexOf(":") + 1, state.indexOf(",")))
								, Integer.valueOf(state.substring(state.indexOf(",") + 1))));
						gp.repaint();
					}
					else if (state.contains("[Turn]:")) {
						gp.Turn = state.substring(state.indexOf(":") + 1);
						GameFrame.pp.repaint();
					}
					else if (state.contains("[Winner]:")) {
						GameFrame.pp.getwinner().showwinner(state.substring(state.indexOf(":") + 1));
						gp.gameover();
					}
					else if (!state.contains(":"))
						GameFrame.pp.getgcp().getarea().setText(GameFrame.pp.getgcp().getarea().getText() + "\n" + state);
				
				} catch(IOException e) {}
			}
		}
	}
}