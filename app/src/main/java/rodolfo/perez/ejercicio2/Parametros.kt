package rodolfo.perez.ejercicio2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Parametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_parametros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uuid=intent.getStringExtra("uuid")
        findViewById<TextView>(R.id.textView11).text=uuid
        val numeroTicket=intent.getIntExtra("numero de ticket",0)
        findViewById<TextView>(R.id.textView6).text=numeroTicket.toString()
        val nombre=intent.getStringExtra("tituloTicket")
        findViewById<TextView>(R.id.textView4).text=nombre
        val descripccion=intent.getStringExtra("descripccion")
        findViewById<TextView>(R.id.textView3).text=descripccion
        val responsable=intent.getStringExtra("responsable")
        findViewById<TextView>(R.id.textView2).text=responsable
        val correo=intent.getStringExtra("correo")
        findViewById<TextView>(R.id.textView7).text=correo
        val telefono=intent.getIntExtra("telefonoAutor",0)
        findViewById<TextView>(R.id.textView8).text=telefono.toString()
        val ubicacion=intent.getStringExtra("ubicacion")
        findViewById<TextView>(R.id.textView9).text=ubicacion
        val estado=intent.getStringExtra("estadaTicket")
        findViewById<TextView>(R.id.textView10).text=estado




    }
}