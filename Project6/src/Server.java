import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Server {
	private HashMap<String, DataOutputStream> clients;
	private int turn;
	
	//constructor
	public Server() {
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;		
		try {
			serverSocket = new ServerSocket(9909);
			System.out.println("������ ���۵Ǿ����ϴ�.");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"���� �����Ͽ����ϴ�.");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void sendToAll(String msg) {
		Iterator<String> it = clients.keySet().iterator();
		
		while(it.hasNext()) {
			try {
				DataOutputStream out = clients.get(it.next());
				out.writeUTF(msg);
			} catch(IOException e) {}
		}
	}
	
	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		//constructor
		public ServerReceiver(Socket socket) {	//accepted socket
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch(IOException e) {}
		}
		
		@Override
		//need to fix(data that should be send to all player)
		public void run() {
			String name = "";
			
			try {
				name = in.readUTF();
				sendToAll("#"+name+"���� �����̽��ϴ�.");
				turn = 0;
				clients.put(name, out);
				
				if (clients.size() % 2 == 1)
					sendToAll("[Color]:Black");
				else {
					sendToAll("[Color]:White");
					sendToAll("[Ready]:");
				}
					
				
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");
				
				while(in!=null) {
					String statement = in.readUTF();
					
					if (statement.contains("Location")) {
						if (++turn % 4 < 2)
							sendToAll("[Turn]:White");
						else
							sendToAll("[Turn]:Black");
					}
					else if(statement.contains("[Initialize turn]:")) {
						turn = 0;
					}
					else if(statement.contains("[Undo]:"))
						turn--;
					
					System.out.println("Turn: " + turn);
					sendToAll(statement);
				}
			} catch(IOException e) {
				
			} finally {	//when client disconnect to server
				sendToAll("#" + name + "���� �����̽��ϴ�.");
				clients.remove(name);
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"���� ������ �����Ͽ����ϴ�.");
				System.out.println("���� ���������� ���� "+clients.size()+"�Դϴ�.");
			}
		}
	}

	public static void main(String args[]) {
		new Server().start();
	}
}