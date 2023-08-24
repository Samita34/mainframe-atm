package bo.edu.ucb.sis213;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bo.edu.ucb.sis213.dao.Conexion;
import bo.edu.ucb.sis213.view.MenuPrincipalGUI;
import bo.edu.ucb.sis213.dao.UsuarioDao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InicioGUI extends JFrame {
	
	

	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passwordField;
	public int usuarioId;

	private int intentos = 3;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioGUI frame = new InicioGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InicioGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 452, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_contentPane.columnWidths = new int[] { 30, 100, 30 };
		gbl_contentPane.rowHeights = new int[] { 30, 30, 30, 30, 30, 30 };
		contentPane.setLayout(gbl_contentPane);
		JLabel lblTitulo = new JLabel("Bienvenido al Cajero Automático.");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.gridwidth = 3;
		gbc_lblTitulo.anchor = GridBagConstraints.NORTH;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitulo.gridx = 0;
		gbc_lblTitulo.gridy = 0;
		contentPane.add(lblTitulo, gbc_lblTitulo);
		JLabel lblUsuario = new JLabel("Alias:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 1;
		contentPane.add(lblUsuario, gbc_lblUsuario);
		userField = new JTextField();
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.insets = new Insets(0, 0, 5, 5);
		gbc_userField.gridx = 1;
		gbc_userField.gridy = 1;
		contentPane.add(userField, gbc_userField);
		userField.setColumns(10);
		JLabel lblContrasea = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 0;
		gbc_lblContrasea.gridy = 2;
		contentPane.add(lblContrasea, gbc_lblContrasea);
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		contentPane.add(passwordField, gbc_passwordField);
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 0, 5);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 4;
		contentPane.add(btnIniciarSesion, gbc_btnIniciarSesion);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioDao func=new UsuarioDao();
				
				try {
					if(func.iniciaSesion(intentos, userField, passwordField)){
						
						MenuPrincipalGUI menu = new MenuPrincipalGUI(usuarioId);
							intentos = 3;
					        
							setContentPane(menu);
							revalidate();
					}
					else{
						intentos--;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				

				/*String alias = userField.getText();
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				int pinIngresado = Integer.parseInt(password);
				try {
					Connection connection = Conexion.getConnection();
					int usuarioId = validarPin(connection, pinIngresado, alias);
					if (usuarioId != -1) {
						MenuPrincipalGUI menu = new MenuPrincipalGUI(connection, usuarioId);
						intentos = 3;
						setContentPane(menu);
						revalidate();
					} else {
						intentos--;
						if (intentos > 0) {
							JOptionPane.showMessageDialog(null,
									"PIN o alias incorrecto. Le quedan " + intentos + " intentos.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"PIN o alias incorrecto. Ha excedido el número de intentos.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}*/
			}
		});
	}
	
	

	public static int validarPin(Connection connection, int pinIngresado, String alias) throws SQLException {
		String query = "SELECT id FROM usuarios WHERE pin = ? and alias = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, pinIngresado);
			preparedStatement.setString(2, alias);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("id");
			} else {
				return -1;
			}
		}
	}
}
