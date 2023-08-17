package bo.edu.ucb.sis213;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MenuPrincipalGUI extends JPanel {

	private JPanel contentPane; // El contentPane que vas a usar

/**
	 * Create the panel.
	 */
	public MenuPrincipalGUI() {
		// Asigna el contentPane proporcionado

		initComponents();
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
		
		JButton button_4 = new JButton("5. Consultas.");
		button_4.setBounds(28, 216, 380, 23);
		add(button_4);
		
		JButton button_5 = new JButton("Salir.");
		button_5.setBounds(28, 250, 380, 23);
		add(button_5);
	}
}
