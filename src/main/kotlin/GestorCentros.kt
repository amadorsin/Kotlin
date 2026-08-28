class GestorCentros {
    val Lista = mutableListOf<CentroCultivo>()

    fun Agregar(centro: CentroCultivo){
        Lista.add(centro)
        println("Centro ${centro.nombre} agregado con éxito")
    }

    fun Listar(){
        if (Lista.isEmpty()){
            println("No hay datos ingresados")
        }
        else{
            println("Lista de centros de cultivos:")
            for (centro in Lista)
                println(centro)
        }
    }

    fun Buscar(id: Int): CentroCultivo?{
        val centro= Lista.find { it.id == id }?:run{
            println("No se encontró ningún ID con: $id")
            return null
        }
        return Lista.find {it.id == id}
    }
}

