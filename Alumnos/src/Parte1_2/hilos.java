package Parte1_2;

public class hilos extends Thread {

	private int inicio;
	private int fin;
	static int sumadostotal=0;
	static int sumatotal=0;

	public hilos(int inicio, int fin, String nombre) {
		super(nombre);
		this.inicio = inicio;
		this.fin = fin;
		System.out.println("Creando hilo: " + getName());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		hilos h1 = new hilos(1, 100, "Hilo 1");
		hilos h2 = new hilos(1, 100, "Hilo 2");
		hilos h3 = new hilos(1, 100, "Hilo 3");

		h1.start();
		h2.start();
		h3.start();
		
		System.out.println("El numero total de elementos sumados es: "+sumadostotal);
		System.out.println("La suma de todos los hilos es: "+sumatotal);
	}

	public void run() {
		String hilo = getName();
		int resultado = 0;
		int sumados=0;
		switch (hilo) {
		case "Hilo 1":
			for (int i = inicio; i < fin; i++) {
				if(i%5==0) {
					resultado=resultado+i;
					sumados++;
				}
			}
			System.out.println("El resultado del hilo: "+getName()+ " ==> "+ resultado);
			System.out.println("Elementos sumados: "+sumados);
			sumatotal=sumatotal+resultado;
			sumadostotal=sumadostotal+sumados;
			break;
		case "Hilo 2":
			for (int i = inicio; i < fin; i++) {
				if(i%2!=0) {
					resultado=resultado + i;
					sumados++;
				}
			}
			System.out.println("El resultado del hilo: "+getName()+ " ==> "+ resultado);
			System.out.println("Elementos sumados: "+sumados);
			sumatotal=sumatotal+resultado;
			sumadostotal=sumadostotal+sumados;
			break;
		case "Hilo 3":
			for (int i = inicio; i < fin; i++) {

			}
			System.out.println("El resultado del hilo: "+getName()+ " ==> "+ resultado);
			System.out.println("Elementos sumados: "+sumados);
			sumatotal=sumatotal+resultado;
			sumadostotal=sumadostotal+sumados;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + hilo);
		}
	}

}
