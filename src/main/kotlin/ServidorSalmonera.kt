import kotlinx.coroutines.delay

object ServidorSalmonera {
    suspend fun consultarProducto(centro: CentroCultivo){
        println("Conectando con el servidor para consultar producción de ${centro.nombre}...")
        delay(2000L)
        println("Datos recibidos: El centro ${centro.nombre} tiene una producción de ${centro.produccionToneladas} toneladas.")
    }

}