package Proyecto2;

import java.awt.BorderLayout;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Font;




import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



public class VentanaPrincipal extends javax.swing.JFrame  {
	
	//Atributos
	
	//Botones
	private JButton BotonConsultas;
	private JButton BotonCajero;
	private JButton BotonPrestamo;
	
	//Paneles
	private Container contentPane;
	private JPanel panelPrincipal;
	private Consultas consultas;
	private CajeroAutomatico cajero;
	

	//Constructor
	public VentanaPrincipal(){
	      super();
	      initGUI();  	      

}

	private void initGUI() {
		//preferencias del frame
		setBounds(200, 100, 800, 600);
		setTitle("Banco");
		setResizable(false);
		
		contentPane= getContentPane();
		contentPane.setLayout(null);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(null);
		panelPrincipal.setVisible(true);
		panelPrincipal.setBounds(0, 0, 800, 600);
		contentPane.add(panelPrincipal);
		
		BotonConsultas = new JButton();
		BotonConsultas.setFont(new Font("Calibri", Font.BOLD, 14));
		panelPrincipal.add(BotonConsultas);
		BotonConsultas.setText("Consultar");
		BotonConsultas.setBounds(20, 5, 182, 23);
		BotonConsultas.setSize(211, 23);
		BotonConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BotonConsultasActionPerformed(evt);
			}
		});



	
		BotonCajero = new JButton();
		BotonCajero.setFont(new Font("Calibri", Font.BOLD, 14));
		panelPrincipal.add(BotonCajero);
		BotonCajero.setText("Cajero automatico");
		BotonCajero.setBounds(289, 5, 212, 23);
		BotonCajero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BotonCajeroActionPerformed(evt);
			}
		});
	
		BotonPrestamo = new JButton();
		BotonPrestamo.setFont(new Font("Calibri", Font.BOLD, 14));
		panelPrincipal.add(BotonPrestamo);
		BotonPrestamo.setText("Administrar Prestamo");
		BotonPrestamo.setBounds(544, 5, 211, 23);
		BotonPrestamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BotonPrestamoActionPerformed(evt);
			}
		});
		
		//Creo el panel consultas
		crearConsultas();
		
		//Creo el panel cajero automatico
		cajeroAutomatico();
		
	}
	
	public void crearConsultas(){
		consultas = new Consultas();
		consultas.setLocation(0, 34);
	    panelPrincipal.add(consultas);
	    consultas.setVisible(false);
	}
	
	public void cajeroAutomatico(){
		cajero = new CajeroAutomatico();
		consultas.setLocation(0, 34);
	    panelPrincipal.add(cajero);
	    consultas.setVisible(false);
	}

	private void BotonConsultasActionPerformed(ActionEvent evt) {
		consultas.setVisible(true);	
		cajero.setVisible(false);
		System.out.println("BotonConsultas.actionPerformed, event="+evt);

	}

	private void BotonCajeroActionPerformed(ActionEvent evt) {
		consultas.setVisible(false);
		cajero.setVisible(true);
		System.out.println("BotonCajero.actionPerformed, event="+evt);

	}

	private void BotonPrestamoActionPerformed(ActionEvent evt) {
//		consultas.setVisible(true);	
//		System.out.println("BotonPrestamo.actionPerformed, event="+evt);

	}
	
	
}	
