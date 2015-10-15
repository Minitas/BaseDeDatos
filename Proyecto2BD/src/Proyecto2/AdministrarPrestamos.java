package Proyecto2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import quick.dbtable.DBTable;


public class AdministrarPrestamos extends javax.swing.JPanel{

	private JTextField textLegajo;
	private JPasswordField fieldPassword;
	private JButton BotonAcceder;
	
	private JLabel labelLegajo;
	private JLabel labelPassword;
	private JLabel titulo;	
	   
	//para conexion base de datos
	private java.sql.Connection cnx;
	
	
	public AdministrarPrestamos(){
	      super();
	      initGUI();
	}
	   
	private void initGUI(){
		setBounds(0, 35, 800,570);
		setVisible(false);
		this.setBackground(new java.awt.Color(255,255,164));
		setLayout(null);
		
		addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
		
		//titulo
		titulo = new JLabel();
		titulo.setText("ADMINISTRACION DE PRESTAMOS");
		titulo.setBounds(44, 59, 716, 60);
		titulo.setFont(new Font("Calibri", Font.BOLD, 50));
		titulo.setForeground(new java.awt.Color(255,128,0));
		this.add(titulo);
		
		//Botones
		BotonAcceder = new JButton();
		add(BotonAcceder);
		BotonAcceder.setText("Acceder");
		BotonAcceder.setBounds(363, 258, 103, 28);
		BotonAcceder.setFont(new Font("Calibri", Font.BOLD, 14));
		BotonAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BotonPINActionPerformed(evt);
			}
		});

		//text field tarjeta		
		textLegajo = new JTextField();
		textLegajo.setBounds(290, 150, 250, 23);
		add(textLegajo);
		
		textLegajo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextTarjetaMouseClicked(evt);
			}
		});
		
		//password
		fieldPassword = new JPasswordField();
		fieldPassword.setText("");
		fieldPassword.setBounds(290, 200, 250, 23);
		add(fieldPassword);
		fieldPassword.setVisible(true);
		fieldPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextPINMouseClicked(evt);
			}
		});

		//Labels
		labelLegajo = new JLabel();
		labelLegajo.setFont(new Font("Calibri", Font.BOLD, 16));
		labelLegajo.setText("Legajo");
		labelLegajo.setBounds(218, 153, 47, 16);
		add(labelLegajo);
		
		labelPassword = new JLabel();
		labelPassword.setFont(new Font("Calibri", Font.BOLD, 16));
		labelPassword.setText("Password");
		labelPassword.setBounds(217, 203, 73, 16);
		add(labelPassword);

	}
	
	private void thisComponentShown(ComponentEvent evt){
		conectarBD();
	}
	private void thisComponentHidden(ComponentEvent evt){
		desconectarBD();
	}
	
	   
	private void conectarBD(){
		System.out.println("por crear\n");
		String driver ="com.mysql.jdbc.Driver";
		String servidor = "'%'";			///////// ????????????????????????????????????????????????????????????????????
		String baseDatos = "banco";
		String usuario = "empleado";
		String clave = "empleado";
		String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
		
		try{
			cnx = java.sql.DriverManager.getConnection(uriConexion, usuario, clave);
		}
		catch(java.sql.SQLException ex){}
		
		System.out.println("Creada!!!!");
     }
	
	private void desconectarBD(){
//		try{
//			//tabla.close();            
//		}
//		catch (SQLException ex){
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
//		}      
	}
	
	private void BotonPINActionPerformed(ActionEvent evt) {
		//Obtengo datos legajo y password
		String legajo= textLegajo.getText();
		String password= fieldPassword.getText();
		
		if(legajo.equals("") || password.equals("")){
			JOptionPane.showMessageDialog(null, "Debe ingresar legajo y password", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
			textLegajo.setText("");  
		}
		else{
			try {
				String pinBD= "";
				//verificar si el pin es valido para la tarjeta
				
				//se crea una sentencia jdbc para realizar la consulta
				java.sql.Statement stmt = cnx.createStatement();
				System.out.println("Base de datos conectada \n");
				
				//se prepara el string SQL de la consulta
				String sql= "SELECT password"
							+ "	FROM Empleado"
							+ "WHERE legajo=1" ;		///////comparar con el legajo obtenido del textfield
				
				//se ejecuta la sentencia y se recibe un resultado
				java.sql.ResultSet rs = stmt.executeQuery(sql);
				
				rs.next();	//avanzo a la primer fila de la tabla del resultado a la consulta
				//hay solo una fila ya que busque un legajo particular, el cual es clave en la tabla empleado
				String passwordBD=rs.getString("password");

				rs.close();
				stmt.close();
				
				if(passwordBD.equals(password)){
					System.out.println("Coinciden");
					//MOSTRAR MENU CON OPCIONES
				}
				else{
					JOptionPane.showMessageDialog(null, "Legajo o password incorrecto", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
					textLegajo.setText("");
					fieldPassword.setText("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void TextPINMouseClicked(MouseEvent evt) {
		fieldPassword.setText("");
	}
	
	private void TextTarjetaMouseClicked(MouseEvent evt) {
		textLegajo.setText("");
	}
	
	
}

