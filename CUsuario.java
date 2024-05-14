/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.mysql.cj.protocol.Resultset;
import com.toedter.calendar.JDateChooser;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isaac
 */
public class CUsuario {
    
    int idSexo;

    public void establecerIdSEXO(int idSexo) {
        this.idSexo = idSexo;
    }
    
    
    public void MostrarSexoCombo(JComboBox comboSexo){
        
        Clases.CConexion objeConexion = new Clases.CConexion();
        
        String sql = "select * from sexo";
        
        Statement st;
        
        try {
            st = objeConexion.establecerConnection().createStatement();
            
            ResultSet rs  =  st.executeQuery(sql);                                        
            comboSexo.removeAllItems();
            
            while (rs.next()){
                String nombreSexo = rs.getString("sexo");
                this.establecerIdSEXO(rs.getInt("id"));
                
                comboSexo.addItem(nombreSexo);
                comboSexo.putClientProperty(nombreSexo, idSexo);
                
                
            } 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al mostrar sexo:"+e.toString());
        }
        finally{
            objeConexion.cerrarConexion();
        }
    }
    public void AgregarUsuario(JTextField nombres,JTextField apellidos,JComboBox combosexo,JTextField edad,JDateChooser fcita,JTextField docnombre,JTextField docapellidos,JTextField especialidad,File foto){
        
        CConexion objetoConexion = new CConexion();
        String consulta="insert into usuarios(nombres,apellidos,fksexo,edad,docnombre,docapellidos,especialidad,fcita,foto) values (?,?,?,?,?,?,?,?,?);";
        
        try {
            FileInputStream fis = new FileInputStream(foto);
            
            CallableStatement cs=objetoConexion.establecerConnection().prepareCall(consulta);
            cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            
            int idSexo= (int) combosexo.getClientProperty(combosexo.getSelectedItem());
            
            cs.setInt(3, idSexo);
            cs.setInt(4, Integer.parseInt(edad.getText()));
            cs.setString(5, docnombre.getText());
            cs.setString(6, docapellidos.getText());
            cs.setString(7, especialidad.getText());
            Date fechaSeleccionada = fcita.getDate();
            
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            
            cs.setDate(8, fechaSQL);
            cs.setBinaryStream(9, fis,(int)foto.length());
            
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "se guardo el usuario correctamente");
                                                                                   
                    
                    } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error al guardar"+e.toString());
        }
    }
    
    public void MostrarUsuarios (JTable tablaTotalUsuario ){
       Clases.CConexion objetoConexion = new Clases.CConexion();
       DefaultTableModel modelo =new DefaultTableModel();
       
      String sql="";
      
       modelo.addColumn("id");
       modelo.addColumn("Nombres");
       modelo.addColumn("Apellidos");
       modelo.addColumn("Sexo");
       modelo.addColumn("Edad");
       modelo.addColumn("DocNombres");
       modelo.addColumn("DocApellidos");
       modelo.addColumn("DocEspecialidad");
       modelo.addColumn("F.Cita");
       modelo.addColumn("Foto");
       
       tablaTotalUsuario.setModel(modelo);
       
       sql="select usuarios.id,usuarios.nombres,usuarios.apellidos,sexo.sexo,usuarios.edad,usuarios.docnombre,usuarios.docapellidos,usuarios.especialidad,usuarios.fcita,usuarios.foto from usuarios INNER JOIN  sexo ON usuarios.fksexo = sexo.id;";
       
        try {
            Statement st = objetoConexion.establecerConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
               String id = rs.getString("id");
               String nombres = rs.getString("nombres");
               String apellidos = rs.getString("apellidos");
               String sexo = rs.getString("sexo");
               String edad = rs.getString("edad");
               String docnombre = rs.getString("docnombre");
               String docapellidos = rs.getString("docapellidos");
               String especialidad = rs.getString("especialidad");
               String fcita = rs.getString("fcita");
               
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date fechaSQL = rs.getDate("fcita");
                String nuevaFecha = sdf.format(fechaSQL);
                
                byte [] imageBytes = rs.getBytes("foto");
                Image foto = null;
                
                if (imageBytes !=null){
                    try {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        foto= imageIcon.getImage();
                                                                      
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error:"+ e.toString());
                        
                    }
                    modelo.addRow(new Object[]{id,nombres,apellidos,sexo,edad,docnombre,docapellidos,especialidad,nuevaFecha,foto});
                                       
                    
                }
                
                tablaTotalUsuario.setModel(modelo);
                
                
                                        
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al mostrar usuarios"+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
                
    
    public void Seleccionar (JTable totalUsuarios, JTextField id,JTextField nombres, JTextField apellidos, JComboBox sexo, JTextField edad,JTextField docnombres,JTextField docapellidos, JTextField especialidad,JDateChooser fcita,JLabel foto){
         
      int fila = totalUsuarios.getSelectedRow();
      if (fila>0){
          
          id.setText(totalUsuarios.getValueAt(fila, 0).toString());
          nombres.setText(totalUsuarios.getValueAt(fila, 1).toString());
          apellidos.setText(totalUsuarios.getValueAt(fila, 2).toString());
          sexo.setSelectedItem(totalUsuarios.getValueAt(fila, 3).toString());
          
          edad.setText(totalUsuarios.getValueAt(fila, 4).toString());
          docnombres.setText(totalUsuarios.getValueAt(fila, 5).toString());
          docapellidos.setText(totalUsuarios.getValueAt(fila, 6).toString());
          especialidad.setText(totalUsuarios.getValueAt(fila, 7).toString());
          
          String fechaString = totalUsuarios.getValueAt(fila, 8).toString();
          
          Image imagen = (Image) totalUsuarios.getValueAt(fila, 9);
          ImageIcon originalIcon = new ImageIcon(imagen);
          int lblanchura = foto.getWidth();
          int lblaltura = foto.getHeight();
          
          Image ImagenEscalada = originalIcon.getImage().getScaledInstance(lblanchura, lblaltura,Image.SCALE_SMOOTH);
          
          foto.setIcon(new ImageIcon(ImagenEscalada));
          
          
          try {
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              Date fechaDate = sdf.parse(fechaString);
              
              fcita.setDate(fechaDate);
              
              
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "error al seleccionar,error"+e.toString());
          }

          
          
          
      }
    }
    public void ModificarUsuario (JTextField id,JTextField nombres, JTextField apellidos, JComboBox combosexo, JTextField edad,JTextField docnombres,JTextField docapellidos, JTextField especialidad,JDateChooser fcita,File foto){
        CConexion objetoConexion = new CConexion();
        String consulta ="UPDATE usuarios SET usuarios.nombres=?,usuarios.apellidos=?,usuarios.fksexo=?,usuarios.edad=?,usuarios.docnombre=?,usuarios.docapellidos=?,usuarios.especialidad=?,usuarios.fcita=?,usuarios.foto=? Where usuarios.id=?";
        
        try {
            FileInputStream fis = new FileInputStream(foto);
            CallableStatement cs = objetoConexion.establecerConnection().prepareCall(consulta);
            
            cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            
            int idsexo = (int) combosexo.getClientProperty(combosexo.getSelectedItem());
            
            cs.setInt(3, idsexo);
            cs.setInt(4, Integer.parseInt(edad.getText()));
            cs.setString(5, docnombres.getText());
            cs.setString(6, docapellidos.getText());
            cs.setString(7, especialidad.getText());
            Date fechaSeleccionada = fcita.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            cs.setDate(8, fechaSQL);
            cs.setBinaryStream(9, fis,(int)foto.length());
            cs.setInt(10, Integer.parseInt(id.getText()));
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "se modifico correctamente");
                   
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "no se modifico correctamente"+e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
           
    }
    public void EliminarUsuario(JTextField id){
        CConexion objeConexion = new CConexion();
        
        String consulta="DELETE FROM usuarios WHERE usuarios.id=?;";
        
        try {
            CallableStatement cs = objeConexion.establecerConnection().prepareCall(consulta);
            
            cs.setInt(1, Integer.parseInt(id.getText()));
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "se elimino correctamente");
            
                   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"no se elimino correctamente,error"+ e.toString());                        
                                                          
       
           }
        finally{
            objeConexion.cerrarConexion();
        }
    }
}     
           
        
    
            
    
        