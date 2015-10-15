package Proyecto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import quick.dbtable.DBTable;


public class CajeroAutomatico extends javax.swing.JPanel{

	private DBTable tablaMovimientos;    
	private JScrollPane scrConsulta;
	
	private JPasswordField jPasswordField1;
	
	private JLabel LabelTarjeta;
	private JComboBox<String> Menu;
	private JLabel LabelPin;
	private JLabel titulo;
	
	private JTextField TextTarjeta;
	
	private JButton BotonAcceder;
	private int NroTarjeta;
	private JLabel LabelSaldo;
	private int PIN;
	
	private ComboBoxModel<String> MenuModel;
	   
	//para conexion base de datos
	private java.sql.Connection cnx;
	
	
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
		
		
		addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
		
		
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

		//Tabla movimientos
		tablaMovimientos= new DBTable();
		tablaMovimientos.setBounds(50, 160, 700, 350);    
		tablaMovimientos.setBackground(Color.WHITE);
		tablaMovimientos.setVisible(false);
		add(tablaMovimientos, BorderLayout.CENTER);   

		//text field tarjeta		
		TextTarjeta = new JTextField();
		TextTarjeta.setBounds(290, 150, 250, 23);
		add(TextTarjeta);
		
		TextTarjeta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextTarjetaMouseClicked(evt);
			}
		});

		//Labels
		LabelPin = new JLabel();
		LabelPin.setText("PIN");
		LabelPin.setBounds(245, 200, 116, 16);
		add(LabelPin);
		
		LabelTarjeta = new JLabel();
		LabelTarjeta.setText("Tarjeta");
		LabelTarjeta.setBounds(245, 150, 46, 16);
		add(LabelTarjeta);
		
		LabelSaldo = new JLabel();
		this.add(LabelSaldo);
		LabelSaldo.setText("Saldo");
		LabelSaldo.setVisible(false);
		LabelSaldo.setBounds(320, 161, 93, 26);
		LabelSaldo.setFont(new java.awt.Font("Calibri",0,18));

		//password
		 jPasswordField1 = new JPasswordField();
		 jPasswordField1.setText("");
		 jPasswordField1.setBounds(290, 200, 250, 23);
		 add(jPasswordField1);
		 jPasswordField1.setVisible(true);
		 jPasswordField1.addMouseListener(new MouseAdapter() {
		 public void mouseClicked(MouseEvent evt) {
			TextPINMouseClicked(evt);
	     }
			});
		
		//titulo
		titulo = new JLabel();
		this.add(titulo);
		
		titulo.setText("CAJERO AUTOMATICO");
		titulo.setBounds(176, 52, 600, 58);
		titulo.setFont(new java.awt.Font("Calibri",1,50));
		titulo.setBackground(Color.MAGENTA);
		titulo.setForeground(new java.awt.Color(0,170,170));
		
		MenuModel = new DefaultComboBoxModel<String>(new String[] {"Consultar Saldo", "Ultimos movimientos", "Movimientos por Periodo" });
		Menu = new JComboBox<String>();
		add(Menu);
		Menu.setVisible(false);
		Menu.setModel(MenuModel);
		Menu.setSelectedIndex(-1);
		Menu.setPreferredSize(new java.awt.Dimension(70, 23));
		Menu.setBounds(300, 120,200,30);
		Menu.setBackground(new java.awt.Color(255,255,255));		
		Menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MenuActionPerformed(evt);
			}
		});

		 } catch (Exception e) {
			 e.printStackTrace();
		 }
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
		String servidor = "localhost:3306";
		String baseDatos = "banco";
		String usuario = "admin";
		String clave = "admin";
		String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
		
		try{
			cnx = java.sql.DriverManager.getConnection(uriConexion, usuario, clave);
		}
		catch(java.sql.SQLException ex){}
		
		System.out.println("Creada!!!!");
     }
	
	private void desconectarBD(){
		try{
			tablaMovimientos.close();            
		}
		catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}      
	}
	
	private void BotonPINActionPerformed(ActionEvent evt) {
		//Obtengo datos pin y tarjeta
		String nroTarjeta= TextTarjeta.getText();
		
		String pin= jPasswordField1.getText();
		System.out.println("PIN: "+pin);
		
		if(nroTarjeta.equals("")){
			JOptionPane.showMessageDialog(null, "Ingrese numero de tarjeta y PIN", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
			TextTarjeta.setText("");  
		}
		else{
			try {
				NroTarjeta= Integer.parseInt(nroTarjeta);
				String pinBD= "";
				//verificar si el pin es valido para la tarjeta
				
				//se crea una sentencia jdbc para realizar la consulta
				java.sql.Statement stmt = cnx.createStatement();
				System.out.println("Base de datos conectada \n");
				
				//se prepara el string SQL de la consulta
				String sql= "SELECT PIN"
							+ "	FROM Tarjeta"
							+ "WHERE nro_tarjeta=1" ;
				
				//se ejecuta la sentencia y se recibe un resultado
				/*java.sql.ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					pinBD = rs.getString("PIN");
				}
				rs.close();
				stmt.close();*/
				
				if(true){ //pinBD.equals(pin)
					System.out.println("Coinciden");
					
					//Desabilitar botones y labels actuales
					LabelTarjeta.setVisible(false);
					LabelPin.setVisible(false);
					jPasswordField1.setVisible(false);
					TextTarjeta.setVisible(false);
					BotonAcceder.setVisible(false);
					
					//Agregar menu
					Menu.setVisible(true);
					//add(tablaMovimientos);
					//tablaMovimientos.setVisible(true);
					
					
				}
				else{
					JOptionPane.showMessageDialog(null, "El numero de tarjeta no coincide con el PIN", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
					TextTarjeta.setText("");
					jPasswordField1.setText("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void TextPINMouseClicked(MouseEvent evt) {
		jPasswordField1.setText("");
	}
	
	private void TextTarjetaMouseClicked(MouseEvent evt) {
		TextTarjeta.setText("");
	}
	
	private void BotonSaldoActionPerformed(ActionEvent evt) {
		System.out.println("BotonSaldo.actionPerformed, event="+evt);
		
	}
	
	private void BotonMovActionPerformed(ActionEvent evt) {
		System.out.println("BotonMov.actionPerformed, event="+evt);
		
	}
	
	private void MenuActionPerformed(ActionEvent evt) {
		String opcion= (String)Menu.getSelectedItem();
		 
		switch (opcion) {
		case "Consultar Saldo":
			System.out.println("SALdoooo");  
			java.sql.Statement stmt;
			String saldo="";
			String text=  "Su saldo es $";
			
			//se crea una sentencia jdbc para realizar la consulta
			try {
				stmt = cnx.createStatement();
				
				System.out.println("Base de datos conectada \n");
				
				//se prepara el string SQL de la consulta
				String sql= "SELECT DSTINCT saldo"
						+ "	FROM Tarjeta, Cliente_CA, Caja_Ahorro"
						+ "WHERE Tarjeta.nro_cliente=Cliente_CA.nro_cliente and Tarjeta.nro_ca=Cliente_CA.nro_Ca and Caja_ahorro.nro_ca=Cliente_CA.nro_ca and Tarjeta.nro_tarjeta=NroTarjeta" ;
				
				//se ejecuta la sentencia y se recibe un resultado
			/*	java.sql.ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					saldo = rs.getString("PIN");
				}
				rs.close();
				stmt.close();*/
			} catch (SQLException e) {
				e.printStackTrace();
			}
			text+= saldo;
			LabelSaldo.setText(text);
			LabelSaldo.setVisible(true);
			tablaMovimientos.setVisible(false);
			break;
		case "Ultimos movimientos":
			System.out.println("movimientosssssss\n");    
			LabelSaldo.setVisible(false);
			tablaMovimientos.setVisible(true);
			try {
				stmt = cnx.createStatement();
				
				System.out.println("Base de datos conectada \n");
				
				//se prepara el string SQL de la consulta
				String sql= "SELECT DSTINCT fecha, hora, tipo, monto, nro_tarjeta, cod_caja, cadestino"
						+ "	FROM trans_cajas_ahorro, Tarjeta, Cliente_CA"
						+ "WHERE trans_cajas_ahorro.nro_cliente=Cliente_CA.nro_cliente and Cliente_CA.nro_cliente=Tarjeta.nro_cliente and nro_tarjeta= NroTarjeta"
						+ "order by fecha DESC" ;
				
				//se ejecuta la sentencia y se recibe un resultado
			/*	java.sql.ResultSet rs = stmt.executeQuery(sql);
				int cant=0;
				while(rs.next() and cant<0){
					saldo = rs.getString("PIN");
					cant++;
				}
				rs.close();
				stmt.close();*/
			} catch (SQLException e) {
				e.printStackTrace();
			}
			  
			
			break;
		case "Movimientos por Periodo":
			System.out.println("Movimientos por periodo\n");
			LabelSaldo.setVisible(false);
			tablaMovimientos.setVisible(false);
			
			break;                        
		}
         
		
	}

}
