package Proyecto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import quick.dbtable.DBTable;

import java.awt.Font;



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
public class Consultas extends javax.swing.JPanel {

	//Atributos
	private JTextArea txtConsulta;
	private JButton botonBorrar;
	private JButton btnEjecutar;
	private DBTable tabla;    
	private JScrollPane scrConsulta;
	   
	   
	public Consultas(){
	      super();
	      initGUI();
	      
	}
	   
	private void initGUI(){

		this.setBounds(0, 0, 800, 570);
		setVisible(true);
		this.setBackground(new java.awt.Color(255,215,215));
		this.setLayout(null);
		
		addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
		
		
		
		botonBorrar = new JButton();
		botonBorrar.setFont(new Font("Calibri", Font.BOLD, 14));
		this.add(botonBorrar);
		botonBorrar.setText("Borrar");
		botonBorrar.setBounds(659, 104, 103, 28);
		
		scrConsulta = new JScrollPane();
		add(scrConsulta);
		
		txtConsulta = new JTextArea();
		add(txtConsulta);
		txtConsulta.setTabSize(3);
		txtConsulta.setColumns(80);
		txtConsulta.setBounds(57, 11, 564,174);
		txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		txtConsulta.setText("SELECT *\n FROM trans_cajas_ahorro\n");
		txtConsulta.setFont(new Font("Calibri", Font.PLAIN, 13));
		txtConsulta.setRows(10);
		
		botonBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtConsulta.setText("");            			
			}
		});

		btnEjecutar = new JButton();
		btnEjecutar.setFont(new Font("Calibri", Font.BOLD, 14));
		add(btnEjecutar);
		btnEjecutar.setText("Ejecutar");
		btnEjecutar.setBounds(659, 42, 103, 28);
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				refrescarTabla();
			}
		});

		 //crea la tabla  
		tabla = new DBTable();
		
		 //Agrega la tabla al frame
		this.add(tabla, BorderLayout.CENTER);           
		
		// setea la tabla para s�lo lectura (no se puede editar su contenido)         
		tabla.setEditable(false);
		tabla.setBackground(Color.WHITE);
		tabla.setBounds(18, 200, 760, 320);
	    
	}

	 
	   private void thisComponentHidden(ComponentEvent evt){
	      desconectarBD();
	   }
	   
	   private void thisComponentShown(ComponentEvent evt){
		      conectarBD();
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
	            
	            //establece una conexi�n con la  B.D. usando directamante una tabla DBTable    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	            
	            System.out.println("despues");
		   }
	       catch (SQLException ex){
	             JOptionPane.showMessageDialog(this, "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

	   private void refrescarTabla(){
	      try{    
	    	  // seteamos la consulta a partir de la cual se obtendr�n los datos para llenar la tabla
	    	  tabla.setSelectSql(txtConsulta.getText().trim());

	    	  // obtenemos el modelo de la tabla a partir de la consulta para 
	    	  // modificar la forma en que se muestran de algunas columnas  
	    	  System.out.println("tabla antes de create colum model from query es : "+ (tabla==null));
	    	  tabla.createColumnModelFromQuery();    	  
	    	  System.out.println("PASEEEE");
	    	  for (int i = 0; i < tabla.getColumnCount(); i++){ // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
	    		 if	 (tabla.getColumn(i).getType()==Types.TIME){    		 
	    		  tabla.getColumn(i).setType(Types.CHAR);  
	  	       	 }
	    		 
	    		 // cambiar el formato en que se muestran los valores de tipo DATE
	    		 if	 (tabla.getColumn(i).getType()==Types.DATE){
	    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
	    		 }
	          }  
	    	  // actualizamos el contenido de la tabla.   	     	  
	    	  tabla.refresh();
	    	  // No es necesario establecer  una conexi�n, crear una sentencia y recuperar el 
	    	  // resultado en un resultSet, esto lo hace autom�ticamente la tabla (DBTable) a 
	    	  // patir  de  la conexi�n y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.
	          

	       }
	      catch (SQLException ex){
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	         JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n", "Error al ejecutar la consulta.", JOptionPane.ERROR_MESSAGE);
	      }
	      
	   }
}
