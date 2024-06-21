package Recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.conexion
import modelo.tbServicio
import rodolfo.perez.ejercicio2.Parametros
import rodolfo.perez.ejercicio2.R

class Adaptador(var Datos:List<tbServicio>):RecyclerView.Adapter<viewHolder>() {

    fun eliminarRegistro(tituloTicket : String, position: Int){

        val listaServicio= Datos.toMutableList()
        listaServicio.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = conexion().Cadenaconexion()

            val deleteServicio= objConexion?.prepareStatement("delete tbservicio where tituloTicket = ?")!!
             deleteServicio.setString(1,tituloTicket)
            deleteServicio.executeUpdate()

        }

         Datos=listaServicio.toList()
         notifyItemRemoved(position)
         notifyDataSetChanged()

    }
    
    fun actualizarRecyclerView(nuevaLista:List<tbServicio>){
        Datos=nuevaLista
        notifyDataSetChanged()
    }

    fun actualizarDespuesDeEditar(uuid: String, nuevoServicio: String, nuevoEstado: String){

        val identificador=Datos.indexOfFirst { it.uuid== uuid }
        Datos[identificador].tituloTicket=nuevoServicio

        notifyItemChanged(identificador)

    }

    fun editarProducto(tituloTicket: String, uuid: String, nuevoEstado: String){
        GlobalScope.launch(Dispatchers.IO) {

            val objConexion=conexion().Cadenaconexion()

            val updateServicio=objConexion?.prepareStatement("update tbservicio set tituloTicket = ? where uuid = ?")!!
            updateServicio.setString(1,tituloTicket)
            updateServicio.setString(2,uuid)
            updateServicio.executeUpdate()

            val commit=objConexion.prepareStatement("commit")
             commit.executeUpdate()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       
        val vista=LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card,parent,false)
       return viewHolder(vista)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val item=Datos[position]
        holder.txtNombreCard.text=item.tituloTicket

        when(item.estadaTicket){
              "Activo" -> holder.imgEditar.setImageResource(R.drawable.ic_editar)
               "Inactivo" -> holder.imgEditar.setImageResource(R.drawable.ic_editar)

        }

        holder.imgEditar.setOnClickListener{
            val alertDialogBuilder=AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Editar ticket")
            alertDialogBuilder.setMessage("Editar nombre del ticket")

            val layout= LinearLayout(holder.itemView.context)
            layout.orientation=LinearLayout.VERTICAL

            val inputTitulo=EditText(holder.itemView.context)
            inputTitulo.setText(item.tituloTicket)
            layout.addView(inputTitulo)

            val estado=Spinner(holder.itemView.context)
            val opcciones= arrayOf("Ativo","Inactivo")
            estado.adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_dropdown_item,opcciones)

            val estadoPosicioon=opcciones.indexOf(item.estadaTicket)
            if (estadoPosicioon >= 0){
                estado.setSelection(estadoPosicioon)
            }

            layout.addView(estado)

            alertDialogBuilder.setView(layout)

            alertDialogBuilder.setPositiveButton("Guardar"){
                dialog,wich ->

                val nuevoTitulo=inputTitulo.text.toString().trim()
                val nuevoEstado=estado.selectedItem.toString()

                if(nuevoTitulo.isNotEmpty()){
                    editarProducto(item.uuid,nuevoTitulo,nuevoEstado)
                    actualizarDespuesDeEditar(item.uuid,nuevoTitulo,nuevoEstado)
                }else{

                    Toast.makeText(holder.itemView.context,"Ingrese un nombre valido",Toast.LENGTH_SHORT).show()
                }
            }

            alertDialogBuilder.setNegativeButton("Cancelar"){
                dialog,wich ->
                dialog.dismiss()
            }

            val alertDialog=alertDialogBuilder.create()
            alertDialog.show()

        }

       val servicio=Datos[position]
        holder.txtNombreCard.text=servicio.tituloTicket

        holder.imgBorrar.setOnClickListener{

            val context=holder.txtNombreCard.context

            val builder=AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estas seguro de eliminar la card?")

            builder.setPositiveButton("Si"){

                dialog, wich ->
                eliminarRegistro(servicio.tituloTicket,position)
            }

             builder.setNegativeButton("No"){
                 dialog, wich ->
                 dialog.dismiss()
             }

            val dialog=builder.create()
             dialog.show()

            holder.itemView.setOnClickListener{
                val context=holder.itemView.context
                val pantalla=Intent(context,Parametros::class.java)
                pantalla.putExtra(
                    "uuid",
                    item.uuid
                )
                pantalla.putExtra(
                    "numero de ticket",
                    item.numeroTicket
                )
                pantalla.putExtra(
                    "tituloTicket",
                    item.tituloTicket
                )
                pantalla.putExtra(
                    "descripcion",
                    item.descripccion
                )
                pantalla.putExtra(
                    "responsable",
                    item.responsable
                )
                pantalla.putExtra(
                    "correo",
                    item.correo
                )
                pantalla.putExtra(
                    "telefonoAutor",
                    item.telefonoAutor
                )
                pantalla.putExtra(
                    "ubicacion",
                    item.ubicacion
                )
                pantalla.putExtra(
                    "estadaTicket",
                    item.estadaTicket
                )

                context.startActivity(pantalla)
            }
        }


    }

}