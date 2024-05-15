/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author isaac
 */
public class CConexion {
    
    Connection conectar = null;
    String usuario="root";
    String contraseña="1234";
    String bd="bdusuarios";
    String ip="localhost";
    String puerto="3306";   
    
    String cadena="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            JOptionPane.showMessageDialog(null, "se conecto correctamente");
            
                   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se conecto correctamente");
        
        }
    return conectar;
    }
    
    public void cerrarConexion(){
        try {
            if (conectar!= null && !conectar.isClosed()){
                conectar.close();
                JOptionPane.showMessageDialog(null, "conexion cerrada");
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no se puedo cerrar la conexion");
        }
    }
    }
    
    


