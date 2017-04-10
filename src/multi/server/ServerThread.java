/*
 * �����ڸ��� 1:1�� ��ȭ�� ���� ������
 * 
 * */
package multi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;


public class ServerThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ServerMain main;
	boolean flag = true;
	
	public ServerThread(Socket socket, ServerMain main) {
		this.socket= socket;
		this.main=main;
	
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Ŭ���̾�Ʈ�� �޽��� �ޱ�
	public void listen(){
		String msg = null;
		try {
			msg= buffr.readLine();
			main.area.append(msg+"\n");
			send(msg); //�ٽ� ������
			
		} catch (IOException e) {
			System.out.println("�б� �Ұ�");

			flag=false; //���� ������ ���̱�!
			main.list.remove(this); //���Ϳ��� �� �����带 ����
			main.area.append("1�� ������ ���� ������ " + main.list.size()+"\n");
			
			//e.printStackTrace();
		}
		
	}
	
	public void send(String msg){
		try {
			for(int i=0; i<main.list.size();i++){
				ServerThread st = main.list.elementAt(i);
				st.buffw.write(msg+"\n");
				st.buffw.flush();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(flag){
			listen();
		}
	}
	
}
