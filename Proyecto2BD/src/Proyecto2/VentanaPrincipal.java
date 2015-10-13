package Proyecto2;

import java.awt.BorderLayout;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Font;


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
	
		BotonCajero = new JButton();
		BotonCajero.setFont(new Font("Calibri", Font.BOLD, 14));
		panelPrincipal.add(BotonCajero);
		BotonCajero.setText("Cajero automatico");
		BotonCajero.setBounds(289, 5, 212, 23);
	
		BotonPrestamo = new JButton();
		BotonPrestamo.setFont(new Font("Calibri", Font.BOLD, 14));
		panelPrincipal.add(BotonPrestamo);
		BotonPrestamo.setText("Administrar Prestamo");
		BotonPrestamo.setBounds(544, 5, 211, 23);
		
		//Creo el panel consultas
		crearConsultas();
		
	}
	
	public void crearConsultas(){
		consultas = new Consultas();
		consultas.setLocation(0, 34);
	    panelPrincipal.add(consultas);
	    this.consultas.setVisible(true);	
	}
	
}	
