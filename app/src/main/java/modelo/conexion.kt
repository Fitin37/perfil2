package modelo

import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager

class conexion {
    fun Cadenaconexion(): Connection?{
     try {
         val url="jdbc:oracle:thin:@192.168.1.6:1521:xe"
         val usuario="fito_DEVELOPER"
         val contrasena="qatar2024"

         val Connection = DriverManager.getConnection(url, usuario, contrasena)
         return Connection
     }catch (e:Exception){
      println("error: $e")
         return null
     }
    }


}