package bo.edu.ucb.sis213.dao;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

public class HistoricoDao {

    public HistoricoDao() {

    }

    public void consultarHistorico(Connection connection, int usuarioId) throws SQLException {
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
    }
}