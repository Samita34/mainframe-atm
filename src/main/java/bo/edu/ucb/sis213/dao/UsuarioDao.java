package bo.edu.ucb.sis213.dao;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import bo.edu.ucb.sis213.view.InicioGUI;
import bo.edu.ucb.sis213.view.MenuPrincipalGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao {
    public UsuarioDao() {
        
    }
    
    

    public int iniciaSesion(Connection connection,int intentos,JTextField userField,JPasswordField passwordField) throws SQLException {
        String alias = userField.getText();
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				int pinIngresado = Integer.parseInt(password);
				
					
					int usuarioId = validarPin(connection,pinIngresado, alias);
					if (usuarioId != -1) {
						
                        return usuarioId;
					} else {
						//intentos--;
						if (intentos > 0) {
							JOptionPane.showMessageDialog(null,
									"PIN o alias incorrecto. Le quedan " + intentos + " intentos.", "Error",
									JOptionPane.ERROR_MESSAGE);
                                    return -1;
                                    
						} else {
							JOptionPane.showMessageDialog(null,
									"PIN o alias incorrecto. Ha excedido el número de intentos.", "Error",
									JOptionPane.ERROR_MESSAGE);
                                    System.exit(0);
                                    
						}
					}
				
                return -1;    
    }
    public  int validarPin(Connection connection, int pinIngresado, String alias) throws SQLException {
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

    public void consultarSaldo(Connection connection,int usuarioId) throws SQLException {
        String query = "SELECT nombre, saldo FROM usuarios WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombreUsuario = resultSet.getString("nombre");
                double saldo = resultSet.getDouble("saldo");
                JOptionPane.showMessageDialog(null, "Usuario: " + nombreUsuario + "\nSaldo actual: $" + saldo);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario.");

            }
        }
    }

    /*public void consultarHistorico(Connection connection, int usuarioId) throws SQLException {
        String query = "SELECT a.tipo_operacion, a.cantidad, a.fecha, b.nombre FROM historico a, usuarios b WHERE b.id = ? and b.id=a.usuario_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String nombreUsuario = "";
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Operación");
            tableModel.addColumn("Cantidad");
            tableModel.addColumn("Fecha");
            tableModel.addColumn("Hora");
            while (resultSet.next()) {
                nombreUsuario = resultSet.getString("nombre");
                String tipoOperacion = resultSet.getString("tipo_operacion");
                double cantidad = resultSet.getDouble("cantidad");
                String[] sep = (resultSet.getString("fecha")).split(" ");
                String fecha = sep[0];
                String hora = sep[1];
                tableModel.addRow(new Object[] { tipoOperacion, cantidad, fecha, hora });
            }

            if (!nombreUsuario.isEmpty()) {
                JTextArea textArea = new JTextArea("Usuario: " + nombreUsuario + "\n\nHistorial:\n");
                textArea.setEditable(false);
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                JTable jTable = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(jTable);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(textArea, BorderLayout.NORTH);
                panel.add(scrollPane, BorderLayout.CENTER);
                JOptionPane.showMessageDialog(null, panel, "Historial del Usuario", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron operaciones para el usuario.");
            }
        }
    }*/

    public void realizarDeposito(Connection connection, int usuarioId, int cantidad) throws SQLException {
        if (cantidad > 0) {
            String saldoQuery = "SELECT saldo FROM usuarios WHERE id = ?";
            try (PreparedStatement saldoStatement = connection.prepareStatement(saldoQuery)) {
                saldoStatement.setInt(1, usuarioId);
                ResultSet saldoResultSet = saldoStatement.executeQuery();
                if (saldoResultSet.next()) {
                    double saldoActual = saldoResultSet.getDouble("saldo");
                    String updateQuery = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setDouble(1, cantidad);
                        updateStatement.setInt(2, usuarioId);
                        updateStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "Depósito realizado con éxito. Su nuevo saldo es: $" + (saldoActual + cantidad));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario.");
                }
            }
        }
    }

    public void realizarRetiro(Connection connection,int usuarioId, int cantidad) throws SQLException {
        if (cantidad > 0) {
            String saldoQuery = "SELECT saldo FROM usuarios WHERE id = ?";
            try (PreparedStatement saldoStatement = connection.prepareStatement(saldoQuery)) {
                saldoStatement.setInt(1, usuarioId);
                ResultSet saldoResultSet = saldoStatement.executeQuery();
                if (saldoResultSet.next()) {
                    double saldoActual = saldoResultSet.getDouble("saldo");
                    if (cantidad > saldoActual) {
                        JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
                    }
                    String updateQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setDouble(1, cantidad);
                        updateStatement.setInt(2, usuarioId);
                        updateStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "Retiro realizado con éxito. Su nuevo saldo es: $" + (saldoActual - cantidad));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario.");
                }
            }
        }
    }

    public void cambiarPIN(Connection connection,int usuarioId, int pinIngresado) throws SQLException {
        String query = "SELECT id FROM usuarios WHERE id = ? AND pin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.setInt(2, pinIngresado);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MenuPrincipalGUI menufunc = new MenuPrincipalGUI();
                int nuevoPin = menufunc.ingresaValor("Ingresar nuevo PIN: ");
                int confirmacionPin = menufunc.ingresaValor("Confirmar nuevo PIN: ");
                if (nuevoPin == confirmacionPin) {
                    String updateQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, nuevoPin);
                        updateStatement.setInt(2, usuarioId);
                        int rowsAffected = updateStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "PIN actualizado con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo actualizar el PIN.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Los PINs no coinciden.");
                }
            } else {
                if(pinIngresado!=0){
                    JOptionPane.showMessageDialog(null, "PIN incorrecto.");
                }
                
            }
        }
    }
}
