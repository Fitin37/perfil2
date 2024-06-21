package modelo

data class tbServicio(
    val uuid: String,
    var numeroTicket : Int,
    var tituloTicket : String,
    var descripccion : String,
    var responsable : String,
    var correo : String,
    var telefonoAutor : Int,
    var ubicacion : String,
    var estadaTicket : String
)
