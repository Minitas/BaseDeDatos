package Proyecto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import quick.dbtable.DBTable;


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
public class CajeroAutomatico extends javax.swing.JPanel{

	private JTextField TextTarjeta;
	private JTextField TextPIN;
	private JButton BotonAcceder;

	private DBTable tabla;    
	private JScrollPane scrConsulta;
	
	private int PIN;
	private JLabel titulo;
	private int NroTarjeta;
	   
	   
	public CajeroAutomatico(){
	      super();
	      initGUI();
	}
	   
	private void initGUI(){
		 try {
		//setPreferredSize(new Dimension(800, 600));
		setBounds(0, 35, 800,570);
		setVisible(false);
		this.setBackground(new java.awt.Color(230,255,255));
		setLayout(null);
		
		//Botones
		BotonAcceder = new JButton();
		add(BotonAcceder);
		BotonAcceder.setText("Acceder");
		BotonAcceder.setBounds(365, 280, 103, 28);
		BotonAcceder.setFont(new Font("Calibri", Font.BOLD, 14));
		BotonAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BotonPINActionPerformed(evt);
			}
		});


		//text fields
		TextPIN = new JTextField();
		TextPIN.setText(" Ingrese numero PIN");
		TextPIN.setBounds(290, 200, 250, 23);
		add(TextPIN);
		TextPIN.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextPINMouseClicked(evt);
			}
		});

		TextTarjeta = new JTextField();
		TextTarjeta.setText(" Ingrese numero tarjeta");
		TextTarjeta.setBounds(290, 150, 250, 23);
		add(TextTarjeta);
		
		TextTarjeta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextTarjetaMouseClicked(evt);
			}
		});

		
		
		//titulo
		titulo = new JLabel();
		this.add(titulo);
		titulo.setText("CAJERO AUTOMATICO");
		titulo.setBounds(200, 60, 600, 60);
		titulo.setFont(new java.awt.Font("Calibri",1,50));
		titulo.setBackground(Color.MAGENTA);
		titulo.setForeground(new java.awt.Color(0,170,170));

		conectarBD();
		
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}

	   private void conectarBD(){
		   System.out.println("por crear\n");
		   String driver ="com.mysql.jdbc.Driver";
			String servidor = "localhost:3306";
		    String baseDatos = "banco";
		    String usuario = "admin";
		    String clave = "admin";
		    String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	      
	   }

	   private void desconectarBD(){
	         try{
	            tabla.close();            
	         }
	         catch (SQLException ex){
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }      
	   }
	   
	   private void BotonPINActionPerformed(ActionEvent evt) {
		  String pin= TextPIN.getText();
		  PIN= Integer.parseInt(pin);
		  
		  String nroTarjeta= TextTarjeta.getText();
		   NroTarjeta= Integer.parseInt(nroTarjeta);
		  
		  //verificar si el pin es valido para la tarjeta
		  
		  
		  
		  System.out.println(PIN);
	   }

	   private void TextPINMouseClicked(MouseEvent evt) {
		   TextPIN.setText("");
	   }
	   
	   private void TextTarjetaMouseClicked(MouseEvent evt) {
		   TextTarjeta.setText("");
	   }

}
