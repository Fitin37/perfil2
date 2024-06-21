package rodolfo.perez.ejercicio2

import Recyclerview.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.contextaware.withContextAvailable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.conexion
import modelo.tbServicio
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //agregar campos

        //LLamar todos los elementos
        val txtnumero=findViewById<EditText>(R.id.txtNumero)
        val txtnombre=findViewById<EditText>(R.id.txtNombre)
        val txtdescripccion=findViewById<EditText>(R.id.txtDesc)
        val txtresponsable=findViewById<EditText>(R.id.txtResponsable)
        val txtxorreo=findViewById<EditText>(R.id.txtCorreo)
        val txttelefono=findViewById<EditText>(R.id.txtTelefono)
        val txtubicacion=findViewById<EditText>(R.id.txtUbicacion)
        val txtestado=findViewById<EditText>(R.id.txtEstado)
        val btnagregar=findViewById<Button>(R.id.btnAgregar)
        val rcvDatos=findViewById<RecyclerView>(R.id.rcvServicio)


        rcvDatos.layoutManager=LinearLayoutManager(this)

        fun obtenerServicios(): List<tbServicio> {
            val objConexion = conexion().Cadenaconexion()

            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from tbservicio")!!

            val listaServicio = mutableListOf<tbServicio>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("uuid")
                val numeroTicket = resulSet.getInt("numeroTicket")
                val tituloTicket = resulSet.getString("tituloTicket")
                val descripccion = resulSet.getString("descripccion")
                val responsable = resulSet.getString("responsable")
                val correo = resulSet.getString("correo")
                val telefonoAutor = resulSet.getInt("telefonoAutor")
                val ubicacion = resulSet.getString("ubicacion")
                val estadaTicket = resulSet.getString("estadaTicket")

                val valores=tbServicio(uuid,numeroTicket,tituloTicket,descripccion,responsable,correo,telefonoAutor,ubicacion,estadaTicket)
                listaServicio.add(valores)
            }

            return listaServicio
        }

             CoroutineScope(Dispatchers.IO).launch {
             val ejecutarFuncion=obtenerServicios()

                 withContext(Dispatchers.Main){
                 val miAdaptador=Adaptador(ejecutarFuncion)
                     rcvDatos.adapter=miAdaptador
                 }
             }





        //programar el boton

        btnagregar.setOnClickListener{
            //Crear una corutina
            CoroutineScope(Dispatchers.IO).launch {
                //crar un onjeto
                val objConexion=conexion().Cadenaconexion()

                  //crear un prepareStateman
                val AgregarProblema=objConexion?.prepareStatement("insert into tbservicio (uuid,numeroTicket,tituloTicket,descripccion,responsable,correo,telefonoAutor,ubicacion,estadaTicket) values(?,?,?,?,?,?,?,?,?)")!!

                AgregarProblema.setString(1,UUID.randomUUID().toString())
                AgregarProblema.setInt(2,txtnumero.text.toString().toInt())
                AgregarProblema.setString(3,txtnombre.text.toString())
                AgregarProblema.setString(4,txtdescripccion.text.toString())
                AgregarProblema.setString(5,txtresponsable.text.toString())
                AgregarProblema.setString(6,txtxorreo.text.toString())
                AgregarProblema.setInt(7,txttelefono.text.toString().toInt())
                AgregarProblema.setString(8,txtubicacion.text.toString())
                AgregarProblema.setString(9,txtestado.text.toString())

                AgregarProblema.executeUpdate()

                val nuevosServicios = obtenerServicios()

                withContext(Dispatchers.Main){
                    (rcvDatos.adapter as? Adaptador)?.actualizarRecyclerView(nuevosServicios)
                }


            }

        }

    }
}