package Proyecto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import quick.dbtable.DBTable;

public class CajeroAutomatico extends javax.swing.JPanel{

	private JTextArea txtConsulta;
	private JButton botonBorrar;
	private JButton btnEjecutar;
	private DBTable tabla;    
	private JScrollPane scrConsulta;
	   
	   
	public CajeroAutomatico(){
	      super();
	      initGUI();
	}
	   
	private void initGUI(){
		 try {
		//setPreferredSize(new Dimension(800, 600));
		setBounds(0, 25, 800,570);
		setVisible(false);
		setBackground(Color.BLUE);
		this.setLayout(null);
	    
		conectarBD();
		
	} catch (Exception e) {
        e.printStackTrace();
     }
	}

	 
	   private void thisComponentHidden(ComponentEvent evt){
	      this.desconectarBD();
	   }

	   private void conectarBD(){
		   try{
			   System.out.println("por crear\n");
			   String driver ="com.mysql.jdbc.Driver";
	        	String servidor = "localhost:3306";
	            String baseDatos = "banco";
	            String usuario = "admin";
	            String clave = "admin";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	   System.out.println("Antes");
	            //establece una conexión con la  B.D. usando directamante una tabla DBTable    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	            System.out.println("despues");
		   }
	       catch (SQLException ex){
	             JOptionPane.showMessageDialog(this,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	         catch (ClassNotFoundException e){
	            e.printStackTrace();
	         }
	      
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

}
