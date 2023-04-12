package Parte1_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class ProcesoPadre {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc =new Scanner(System.in);
		String texto;
		int numero;
		String codificado;
		Process p = null;
		
		ProcessBuilder hijo;
		InputStream inStream;
		InputStreamReader inStreamReader;
		BufferedReader br;
		
		OutputStream outStream;
		OutputStreamWriter outStreamWriter;
		BufferedWriter bw;
		
		try {
			File directorio = new File(".\\bin");
			
			hijo = new ProcessBuilder("java", "ProcesoHijo");
			hijo.directory(directorio);
			p = hijo.start();
			
			outStream = p.getOutputStream();
			outStreamWriter = new OutputStreamWriter(outStream,"UTF-8");
			bw = new BufferedWriter(outStreamWriter);
			
			inStream = p.getInputStream();
			inStreamReader = new InputStreamReader(inStream,"UTF-8");
			br = new BufferedReader(inStreamReader);
			
			System.out.println("Escribe un texto:");
			texto=sc.next();
			bw.write(texto);
			
			System.out.println("Escribe un numero:");
			numero=sc.nextInt();
			bw.write(numero);
			
			bw.flush();
			
			while ((codificado = br.readLine()) != null) {
				System.out.println("El texto codificado es: "+codificado);
				}
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		sc.close();
	}

}
