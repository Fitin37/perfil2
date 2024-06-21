package rodolfo.perez.ejercicio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.conexion

class Registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgAtrasarRegistrarse=findViewById<ImageView>(R.id.imgVolver)
        val txtNombreRegistro=findViewById<EditText>(R.id.txtNombreRegistro)
        val txtApellidosRegistro=findViewById<EditText>(R.id.txtapellidosRegistro)
        val txtEdad=findViewById<EditText>(R.id.txtEdadRegistro)
        val txtCorreo=findViewById<EditText>(R.id.txtCorreoRegistro)
        val txtContrasenaRegistro=findViewById<EditText>(R.id.txtContraseñaRegistro)
        val btnCrearCuenta=findViewById<Button>(R.id.btnCrearCuenta)
        val btnVolver=findViewById<Button>(R.id.btnRegresarLogin)


        btnCrearCuenta.setOnClickListener{
           GlobalScope.launch(Dispatchers.IO) {
               val objconexion = conexion().Cadenaconexion()

               val crearUsuario =
                   objconexion?.prepareStatement("insert into usuario(nombre,apellidos,edad,correo,contraseña) values(?,?,?,?,?)")!!
               crearUsuario.setString(1, txtNombreRegistro.text.toString())
               crearUsuario.setString(2, txtApellidosRegistro.text.toString())
               crearUsuario.setString(3, txtEdad.text.toString())
               crearUsuario.setString(4, txtCorreo.text.toString())
               crearUsuario.setString(5, txtContrasenaRegistro.text.toString())
               crearUsuario.executeUpdate()
               withContext(Dispatchers.Main) {
                   Toast.makeText(
                       this@Registrarse,
                       "usuario creado exitosamente",
                       Toast.LENGTH_SHORT
                   ).show()
                   txtNombreRegistro.setText("")
                   txtContrasenaRegistro.setText("")
               }
           }
            imgAtrasarRegistrarse.setOnClickListener{
                val PantallaLogin=Intent(this,Login::class.java)
                startActivity(PantallaLogin)
            }
        }
        btnVolver.setOnClickListener{
            val pantallaLogin=Intent(this,Login::class.java)
            startActivity(pantallaLogin)
        }
    }
}