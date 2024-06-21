package rodolfo.perez.ejercicio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.conexion

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombre=findViewById<EditText>(R.id.txtNombreLogin)
        val txtContrasenaLogin=findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnIngresar=findViewById<Button>(R.id.btnIngresar)
        val btnRegistrarme=findViewById<Button>(R.id.btnRegistrarme)

        btnIngresar.setOnClickListener{
            val pantallaPrincipal=Intent(this,MainActivity::class.java)
            GlobalScope.launch(Dispatchers.IO){
                val objconexion=conexion().Cadenaconexion()
                val comprobarUsuario=objconexion?.prepareStatement("select * from usuario where nombre =? and contraseña=?")!!
                comprobarUsuario.setString(1,txtNombre.text.toString())
                comprobarUsuario.setString(2,txtContrasenaLogin.text.toString())
                val resultado=comprobarUsuario.executeQuery()
                if (resultado.next()){
                    startActivity(pantallaPrincipal)
                }else{
                    println("Usuario no encontrado,verifique bien las credenciales")
                }
            }
        }
        btnRegistrarme.setOnClickListener{
            val pantallaRegistrarme=Intent(this,Registrarse::class.java)
            startActivity(pantallaRegistrarme)
        }
    }
}