package bo.edu.ucb.sis213.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import bo.edu.ucb.sis213.dao.Conexion;
import bo.edu.ucb.sis213.dao.HistoricoDao;
import bo.edu.ucb.sis213.dao.UsuarioDao;

public class MenuPrincipalGUI extends JPanel {// El contentPane que vas a usar
	private UsuarioDao func = new UsuarioDao();
	
	private int usuarioId;
	private Connection connection;

	public MenuPrincipalGUI(Connection connection,int usuarioId) {
		this.connection=connection;
		
		this.usuarioId = usuarioId;
		initComponents();
	}

	public MenuPrincipalGUI() {
	}

	private void initComponents() {
		
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Menu Principal");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel.setBounds(127, 28, 180, 23); // Cambia la posición vertical
		add(lblNewLabel);
		JButton button = new JButton("1. Consultar saldo.");
		button.setBounds(28, 80, 380, 23);
		add(button);
		JButton button_1 = new JButton("2. Realizar un depósito.");
		button_1.setBounds(28, 114, 380, 23);
		add(button_1);
		JButton button_2 = new JButton("3. Realizar un retiro.");
		button_2.setBounds(28, 148, 380, 23);
		add(button_2);
		JButton button_3 = new JButton("4. Cambiar PIN.");
		button_3.setBounds(28, 182, 380, 23);
		add(button_3);
		JButton button_4 = new JButton("5. Consultar historico.");
		button_4.setBounds(28, 216, 380, 23);
		add(button_4);
		JButton button_5 = new JButton("Salir.");
		button_5.setBounds(28, 250, 380, 23);
		add(button_5);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					func.consultarSaldo(connection,usuarioId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					func.realizarDeposito(connection,usuarioId, ingresaValor("Ingresar cantidad a depositar: $"));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					func.realizarRetiro(connection, usuarioId, ingresaValor("Ingresar cantidad a retirar: $"));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					func.cambiarPIN(connection, usuarioId, ingresaValor("Ingrese su PIN actual: "));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistoricoDao histo=new HistoricoDao();
				try {
					histo.consultarHistorico( connection,usuarioId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public int ingresaValor(String msg) {
		while (true) {
			String userInput = JOptionPane.showInputDialog(null, msg, "Entrada de Texto", JOptionPane.PLAIN_MESSAGE);
			if (userInput != null) {
				if (userInput.equals("")) {
					JOptionPane.showMessageDialog(null, "Debe ingresar un valor", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int val = Integer.parseInt(userInput);
					if (val > 0) {
						return val;
					} else {
						JOptionPane.showMessageDialog(null, "Valor invalido.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				return 0;
			}
		}
	}
}
