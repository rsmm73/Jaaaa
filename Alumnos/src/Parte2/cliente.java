package Parte2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

public class cliente extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField mensajeenviado;
	private JTextField respuestaser;

	private Socket socket = null;

	private DataInputStream fentrada; // para leer mensajes de todos
	private DataOutputStream fsalida;

	private boolean repetir = true;
	static int id;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public cliente(Socket s) {
		initComponents();
		id = servidor.id;
		socket = s;

		try {
			fentrada = new DataInputStream(socket.getInputStream());
			fsalida = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, "Error de E/S {0}", e);
			System.exit(0);
		}
	}

	public void run() {
		String texto;
		while (repetir) {
			try {
				texto = fentrada.readUTF();
				respuestaser.setText(texto);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor\n" + e.getMessage(),
						"«Mensaje de error:2»", JOptionPane.ERROR_MESSAGE);
				repetir = false;
			}
		}
		try {
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, "Error al cerrar socket {0}", e);
		}
	}

	private void initComponents() {
		setTitle("CLIENTE: " + id);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 206);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Escribe un texto");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(27, 30, 172, 14);
		contentPane.add(lblNewLabel);

		mensajeenviado = new JTextField();
		mensajeenviado.setBounds(27, 67, 255, 33);
		contentPane.add(mensajeenviado);
		mensajeenviado.setColumns(10);

		respuestaser = new JTextField();
		respuestaser.setEditable(false);
		respuestaser.setBounds(27, 111, 254, 34);
		contentPane.add(respuestaser);
		respuestaser.setColumns(10);

		JButton enviarmensaje = new JButton("Enviar");
		enviarmensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonEnviarActionPerformed(e);
			}
		});
		enviarmensaje.setBounds(307, 10, 117, 34);
		contentPane.add(enviarmensaje);

		JButton salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSalirActionPerformed(e);
			}
		});
		salir.setBounds(307, 55, 117, 34);
		contentPane.add(salir);

		JButton limpiarcampos = new JButton("Limpiar");
		limpiarcampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensajeenviado.setText("");
				respuestaser.setText("");
			}
		});
		limpiarcampos.setBounds(307, 100, 117, 34);
		contentPane.add(limpiarcampos);
	}

	private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {
		String texto = mensajeenviado.getText();
		try {
			fsalida.writeUTF(texto);
		} catch (IOException el) {
			Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, "Error al enviar mensaje {0}", el);
		}
	}

	private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {
		String texto = "Se Desconecta IP: " + socket.getInetAddress() + ", Puerto Remoto: " + socket.getPort()
				+ " con ID => " + id;
		try {
			fsalida.writeUTF(texto);
			fsalida.writeUTF("*");

			repetir = false;
		} catch (IOException el) {
			Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, "Error al enviar mensaje de salida {0}", el);
		}
	}

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			int puerto = 44444;
			Socket s = null;
			try {
				s = new Socket("localhost", puerto);

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor\n" + e.getMessage(),
						"«Mensaje de error:1»", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

			cliente cliente = new cliente(s);
			cliente.setLocationRelativeTo(null);
			cliente.setVisible(true);

			new Thread(cliente).start();

		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// handle exception
			Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, "Error al establecer el LookAndFeel {0}", e);
			JOptionPane.showMessageDialog(null,
					"Se ha producido un error al establecer el formato de ventana. \n" + "No se puede continuar",
					"Error de login", JOptionPane.ERROR_MESSAGE);
		}
	}

}
