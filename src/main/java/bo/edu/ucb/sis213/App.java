package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.sql.SQLException;

import bo.edu.ucb.sis213.dao.Conexion;
import bo.edu.ucb.sis213.view.InicioGUI;

public class App {

    public static void main(String[] args) {
        try {
            Connection connection = Conexion.getConnection();
            InicioGUI frame = new InicioGUI(connection);
            frame.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
