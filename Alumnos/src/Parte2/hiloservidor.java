package Parte2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


public class hiloservidor extends Thread {

	DataInputStream fentrada;
	Socket socket = null;
	
	public hiloservidor(Socket s) {
		socket = s;
		try {
			fentrada = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
		}
	}
	
	public void run() {

		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {
					servidor.actuales--;
					break;
				}
				
				
				EnviarMensajes(cadena);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public static void EnviarMensajes(String texto) {
		Socket s = servidor.tabla[cliente.id];
			try {
				DataOutputStream fsalida = new DataOutputStream(s.getOutputStream());
				if(texto!="*") {
					char []letras = null;
					String codificado = "";
					for(int i = 0; i<texto.length();i++) {
						letras[i]=texto.charAt(i);
					}
					char []cod=letras;
					for (int j=0; j<texto.length();j++) {
						cod[cod.length-j]=letras[j];
						
					}
					for(int i = 0; i<texto.length();i++) {
						codificado=codificado+cod[i];
					}
					
					fsalida.writeUTF(codificado);
				}else {
					fsalida.writeUTF(texto);
				}
			} catch (SocketException se) {
			} catch (IOException e) {
				e.printStackTrace();
			}

		
		
	}
}
