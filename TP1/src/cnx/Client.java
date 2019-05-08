package cnx;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;


@SuppressWarnings("serial")
public class Client extends Applet implements Runnable,KeyListener{
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	int playerid;
	boolean left,right,up,down;
	int playerx;
	int playery;
	
	int[]x=new int[10];
	int[]y=new int[10];
	public void init() {
		setSize(400,400);
		addKeyListener(this);
		try {
		System.out.println("connection en cours...");
		socket=new Socket("localhost",5000);
		System.out.println("connecté");
		in=new DataInputStream(socket.getInputStream());
		playerid=in.readInt();
		out=new DataOutputStream(socket.getOutputStream());
		Input input=new Input(in,this);
		Thread thread =new Thread(input);
		thread.start();
		Thread thread2=new Thread(this);
		thread2.start();
	}catch (Exception e){
		System.out.println("le client ne peut pas demarrer!");
	}
		
	}
public void updateCoordinates(int pid ,int x2 , int y2) {
	this.x[pid]=x2;
	this.y[pid]=y2;
	
}
public void paint ( Graphics g) {
	for (int i=0;i<10;i++) {
		g.drawOval(x[i], y[i], 5, 5);
	}
}
@Override
public void run() {
	while(true) {
		if(right ==true) {
			playerx+=10;
		}
		if(left==true) {
			playerx-=10;
		}
		if(right ==true) {
			playery+=10;
		}
		if(left==true) {
			playery-=10;
		}
		if (right||left||up||down) {
			try {
			out.writeInt(playerid);
			out.writeInt(playerx);
			out.writeInt(playery);
		}catch(Exception e) {System.out.println("probleme d envoie d coordonnées");}}
		repaint();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()==37) {
		left=true;
	}
	if(e.getKeyCode()==38) {
		up=true;
	}
	if(e.getKeyCode()==39) {
		right=true;
	}
	if(e.getKeyCode()==40) {
		down=true;
	}
}

public void keyReleased(KeyEvent e) {
	if(e.getKeyCode()==37) {
		left=false;
	}
	if(e.getKeyCode()==38) {
		up=false;
	}
	if(e.getKeyCode()==39) {
		right=false;
	}
	if(e.getKeyCode()==40) {
		down=false;
	}
	
}

public void keyTyped(KeyEvent e) {
	
	
}
}
class Input implements  Runnable{
	DataInputStream in;
	Client client;
	public Input(DataInputStream in,Client c ) {
		this.in=in;
		this.client=c;
	}
	public void run() {
		while(true) {
			@SuppressWarnings("unused")
			String message ;
			try {
				int playerid=in.readInt();
				int x=in.readInt();
				int y=in.readInt();
				client.updateCoordinates(playerid, x, y);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		
		
	}
}