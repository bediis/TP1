package cnx;
import java.io.*;
import java.net.*;


public class Serveur {
	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;
	static Users[] user=new Users[10];
	public static void main (String[] args ) throws Exception{
		System.out.println("lancement du serveur...");
		serverSocket=new ServerSocket(5000);
		System.out.println("serveur lancé");
		while(true) {
		socket=serverSocket.accept();
		for (int i=0;i<10;i++) {
			if (user[i]==null) {
			
		System.out.println("connection a partir  "+ socket.getLocalAddress());
		out=new DataOutputStream(socket.getOutputStream());
in = new DataInputStream(socket.getInputStream());
	user[i]=new Users(out,in,user,i);
	Thread thread=new Thread(user[i]); 
	thread.start();
	break;
}}
}}
		}
class Users implements Runnable {
	
DataOutputStream out;
DataInputStream in;
String name;
Users[] user=new Users[10];
int playerid;
int playeridin;
int xin;
int yin;
public Users(DataOutputStream out,DataInputStream in,Users[] user,int pid) {
	this.out=out;
	this.in=in;
	this.user=user;
this.playerid=pid;
}
public void run() {
	try {
		out.writeInt(playerid);
	} catch (IOException e1) {
System.err.println("l id d joueur ne peut pas etre envoyer!");
	}
while(true) {
	try {
		playeridin=in.readInt();
		xin=in.readInt();
		yin=in.readInt();
		for (int i=0;i<10;i++) {
			if(user[i]!=null) {
				user[i].out.writeInt(playeridin);
				user[i].out.writeInt(xin);
				user[i].out.writeInt(yin);
			}
		}
	} catch (IOException e) {
		user[playerid]=null;
		break;
	}
	
}
	
}
	
	
	

	
	
}
