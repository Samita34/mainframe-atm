package bo.edu.ucb.sis213.bl;

import javax.swing.JOptionPane;

public class AppBl {
    public AppBl(){}
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
