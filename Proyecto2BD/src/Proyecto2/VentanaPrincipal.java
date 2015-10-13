package Proyecto2;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.SwingUtilities;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class VentanaPrincipal extends javax.swing.JFrame  {
	private JButton BotonConsultas;
	private JButton BotonCajero;
	private JButton BotonPrestamo;
	
	private Consultas consultas;

	public static void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            VentanaPrincipal inst = new VentanaPrincipal();
	            inst.setLocationRelativeTo(null);
	            inst.setVisible(true);
	         }
	      });
	   
	}
	
	public VentanaPrincipal(){
	      super();
	      
	      initGUI();
	      
	      consultas = new Consultas();
	      consultas.setBounds(0 ,25,300,300);
	      getContentPane().add(consultas, BorderLayout.NORTH);
	      this.consultas.setVisible(true);
	   
	   
	      

}
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.setSize(651, 477);
				this.setTitle("Banco");
				{
					BotonConsultas = new JButton();
					getContentPane().add(BotonConsultas);
					BotonConsultas.setText("Consultar");
					BotonConsultas.setBounds(0, 0, 201, 23);
					BotonConsultas.setSize(211, 23);
				}
				{
					BotonCajero = new JButton();
					getContentPane().add(BotonCajero);
					BotonCajero.setText("Cajero automatico");
					BotonCajero.setBounds(212, 0, 212, 23);
				}
				{
					BotonPrestamo = new JButton();
					getContentPane().add(BotonPrestamo);
					BotonPrestamo.setText("Administrar Prestamo");
					BotonPrestamo.setBounds(424, 0, 211, 23);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}	
