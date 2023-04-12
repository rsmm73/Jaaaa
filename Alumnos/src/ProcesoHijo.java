

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcesoHijo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OutputStreamWriter outStreamWriter = null;
		BufferedWriter bw;
		InputStreamReader isr = null;
		String codificado;
		String texto = "";
		int numero = 0;
		char[] letras = null;
		
		try {
			isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			outStreamWriter = new OutputStreamWriter(System.out);
			
			bw = new BufferedWriter(outStreamWriter);
			
			texto=br.readLine();
			numero=br.read();
			
			for(int i = 0; i>texto.length();i++) {
				letras[i]=texto.charAt(i);
			}
			
			for(int i = 0; i>letras.length;i++) {
				letras[i]=(char) (letras[i]+numero);
			}
			
			codificado=letras.toString();
			
			bw.write(codificado);
			bw.flush();
			
		} catch (IOException e) {
			System.err.println("Error: "+e.getMessage());
		}
	}

}
