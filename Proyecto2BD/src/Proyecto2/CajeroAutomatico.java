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
import javax.swing.BorderFactory;
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

	private DBTable tablaMovimientos;    
	private JScrollPane scrConsulta;
	
	private JPasswordField jPasswordField1;
	
	private JLabel LabelTarjeta;
	private JComboBox Menu;
	private JLabel LabelPin;
	private JLabel titulo;
	
	private JTextField TextTarjeta;
	
	private JButton BotonAcceder;
	private int NroTarjeta;
	private JTextField TextSaldo;
	private int PIN;
	
	private ComboBoxModel MenuModel;
	   
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

		

		//text field tarjeta		
		TextTarjeta = new JTextField();
		TextTarjeta.setBounds(290, 150, 250, 23);
		add(TextTarjeta);
		
		TextTarjeta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				TextTarjetaMouseClicked(evt);
			}
		});
		

		TextSaldo = new JTextField();
		TextSaldo.setBounds(414, 138, 116, 23);
		TextSaldo.setVisible(false);
		TextSaldo.setBackground(new java.awt.Color(230,255,255));
		add(TextSaldo);
		TextSaldo.setBorder(BorderFactory.createCompoundBorder(
				null, 
				null));
		TextSaldo.setFont(new java.awt.Font("Calibri",0,16));

		//Labels
		LabelPin = new JLabel();
		LabelPin.setText("PIN");
		LabelPin.setBounds(245, 200, 116, 16);
		add(LabelPin);
		
		LabelTarjeta = new JLabel();
		LabelTarjeta.setText("Tarjeta");
		LabelTarjeta.setBounds(245, 150, 46, 16);
		add(LabelTarjeta);
		
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
		titulo.setBounds(200, 60, 600, 60);
		titulo.setFont(new java.awt.Font("Calibri",1,50));
		titulo.setBackground(Color.MAGENTA);
		titulo.setForeground(new java.awt.Color(0,170,170));
		
		MenuModel = 
				new DefaultComboBoxModel(
						new String[] { "Consultar Saldo", "Ultimos movimientos", "Movimientos por Periodo" });
		Menu = new JComboBox();
		add(Menu);
		Menu.setVisible(false);
		Menu.setModel(MenuModel);
		Menu.setBounds(302, 60, 24, 23);
		Menu.setPreferredSize(new java.awt.Dimension(70, 23));

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
					TextSaldo.setVisible(true);
					TextSaldo.setText("Holiiiiiiiii");
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
		//TODO add your code for BotonSaldo.actionPerformed
	}
	
	private void BotonMovActionPerformed(ActionEvent evt) {
		System.out.println("BotonMov.actionPerformed, event="+evt);
		//TODO add your code for BotonMov.actionPerformed
	}

}
