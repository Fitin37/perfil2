package Recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rodolfo.perez.ejercicio2.R

open class viewHolder(view: View):RecyclerView.ViewHolder(view) {

    val txtNombreCard:TextView =view.findViewById<TextView>(R.id.txtNombrecard)
    val imgEditar:ImageView=view.findViewById(R.id.imgEditar)
    val imgBorrar:ImageView=view.findViewById(R.id.imgBorrar)
}