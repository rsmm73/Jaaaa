package Parte2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servidor {
	
	static ServerSocket servidor;
	static final int PUERTO = 44444;
	static int conexiones = 0;
	static int actuales = 0;
	static int MAXIMO = 10;
	static int id = 1;

	static Socket tabla[] = new Socket[10];

	private static void abreServidor() {
		try {
			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado...");
			while (conexiones < MAXIMO) {
				@SuppressWarnings("resource")
				Socket s = new Socket();
				try {
					s = servidor.accept();
				} catch (SocketException ns) {
					break;
				} catch (IOException ex) {
					Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
					break;
				}
				
				System.out.println("Se conecta IP: "+s.getInetAddress()+", Puerto Remoto: "+s.getPort()+" con ID => "+id);

				tabla[conexiones] = s;
				conexiones++;
				actuales++;
				id++;

				hiloservidor hilo = new hiloservidor(s);
				hilo.start();
			}
		} catch (IOException ex) {
			Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (!servidor.isClosed()) {
			try {
				servidor.close();
			} catch (IOException el) {
				el.printStackTrace();
			}
		}
		System.out.println("Servidor finalizado...");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abreServidor();
	}
	

}
