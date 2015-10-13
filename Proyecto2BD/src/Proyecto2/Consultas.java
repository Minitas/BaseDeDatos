package Proyecto2;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.WindowConstants;
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
public class Consultas extends javax.swing.JPanel {

	 	
	   private JTextArea txtConsulta;
	   private JButton botonBorrar;
	   private JButton btnEjecutar;
	   private DBTable tabla;    
	   private JScrollPane scrConsulta;

	   
	   
	   public Consultas() 
	   {
	      super();
	      initGUI();
	   }
	   
	   private void initGUI() 
	   {
	      try {
	         setPreferredSize(new Dimension(800, 600));
	         this.setBounds(0, 0, 800, 600);
	         setVisible(true);
	         BorderLayout thisLayout = new BorderLayout();
	         this.setLayout(thisLayout);
	         this.addComponentListener(new ComponentAdapter() {
	            public void componentHidden(ComponentEvent evt) {
	               thisComponentHidden(evt);
	            }
	            public void componentShown(ComponentEvent evt) {
	               thisComponentShown(evt);
	            }
	         });
	         {
	           

	            {
	               scrConsulta = new JScrollPane();
	               this.add(scrConsulta);
	               {
	                  txtConsulta = new JTextArea();
	                  scrConsulta.setViewportView(txtConsulta);
	                  txtConsulta.setTabSize(3);
	                  txtConsulta.setColumns(80);
	                  txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
	                  txtConsulta.setText("SELECT *\n" +
	                                      "FROM trans_cajas_ahorro \n");
	                  txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
	                  txtConsulta.setRows(10);
	               }
	            }
	            {
	               btnEjecutar = new JButton();
	               this.add(btnEjecutar);
	               btnEjecutar.setText("Ejecutar");
	               btnEjecutar.addActionListener(new ActionListener() {
	                  public void actionPerformed(ActionEvent evt) {
	                     btnEjecutarActionPerformed(evt);
	                  }
	               });
	            }
	            {
	            	botonBorrar = new JButton();
	            	this.add(botonBorrar);
	            	botonBorrar.setText("Borrar");            
	            	botonBorrar.addActionListener(new ActionListener() {
	            		public void actionPerformed(ActionEvent arg0) {
	            		  txtConsulta.setText("");            			
	            		}
	            	});
	            }	
	         }
	         {
	        	// crea la tabla  
	        	tabla = new DBTable();
	        	
	        	// Agrega la tabla al frame
	            this.add(tabla, BorderLayout.CENTER);           
	                      
	           // setea la tabla para sólo lectura (no se puede editar su contenido)  
	           tabla.setEditable(false);       
	           tabla.setBackground(new java.awt.Color(255,0,255));

	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	   private void thisComponentShown(ComponentEvent evt) 
	   {
	      this.conectarBD();
	   }
	   
	   private void thisComponentHidden(ComponentEvent evt) 
	   {
	      this.desconectarBD();
	   }

	   private void btnEjecutarActionPerformed(ActionEvent evt) 
	   {
	      this.refrescarTabla();      
	   }
	   
	   private void conectarBD()
	   {
	         try
	         {
	            String driver ="com.mysql.jdbc.Driver";
	        	String servidor = "localhost:3306";
	            String baseDatos = "banco";
	            String usuario = "admin";
	            String clave = "admin";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	   
	            //establece una conexión con la  B.D. usando directamante una tabla DBTable    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);
	           
	         }
	         catch (SQLException ex)
	         {
	             JOptionPane.showMessageDialog(this,
	                                           "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
	                                           "Error",
	                                           JOptionPane.ERROR_MESSAGE);
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	         catch (ClassNotFoundException e)
	         {
	            e.printStackTrace();
	         }
	      
	   }

	   private void desconectarBD()
	   {
	         try
	         {
	            tabla.close();            
	         }
	         catch (SQLException ex)
	         {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }      
	   }

	   private void refrescarTabla()
	   {
	      try
	      {    
	    	  // seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
	    	  tabla.setSelectSql(this.txtConsulta.getText().trim());

	    	  // obtenemos el modelo de la tabla a partir de la consulta para 
	    	  // modificar la forma en que se muestran de algunas columnas  
	    	  tabla.createColumnModelFromQuery();    	    
	    	  for (int i = 0; i < tabla.getColumnCount(); i++)
	    	  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
	    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
	    		 {    		 
	    		  tabla.getColumn(i).setType(Types.CHAR);  
	  	       	 }
	    		 // cambiar el formato en que se muestran los valores de tipo DATE
	    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
	    		 {
	    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
	    		 }
	          }  
	    	  // actualizamos el contenido de la tabla.   	     	  
	    	  tabla.refresh();
	    	  // No es necesario establecer  una conexión, crear una sentencia y recuperar el 
	    	  // resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
	    	  // patir  de  la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.
	          
	    	  
	    	  
	       }
	      catch (SQLException ex)
	      {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	         JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                                       ex.getMessage() + "\n", 
	                                       "Error al ejecutar la consulta.",
	                                       JOptionPane.ERROR_MESSAGE);
	         
	      }
	      
	   }
}
