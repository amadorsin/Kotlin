import kotlinx.coroutines.runBlocking

fun main() {
    val gestorCentros = GestorCentros()
    var opcion: Int = 0

    println("Bienvenido al sistema de control AustralMar.")

    do {
        println("\n--- MENÚ PRINCIPAL ---")
        println("1 | Registrar centro")
        println("2 | Listar centros")
        println("3 | Buscar centro por ID")
        println("4 | Consultar producción en servidor")
        println("5 | Salir")
        print("Elige una opción: ")

        try {
            opcion = readln().toInt()
        } catch (e: NumberFormatException) {
            println("Error: Debes ingresar un número válido para la opción del menú.")
            continue
        }

        when (opcion) {
            1 -> {
                val id: Int
                val produccion: Int

                try {
                    print("Ingresa el ID del centro: ")
                    id = readln().toInt()

                    if (gestorCentros.Lista.any { it.id == id }) {
                        println("Error: Ya existe un centro registrado con el ID $id. Registro cancelado.")
                        continue
                    }

                    print("Ingresa el nombre del centro: ")
                    val nombre = readln()

                    print("Ingresa la ubicación: ")
                    val ubicacion = readln()

                    print("Ingresa la producción en toneladas: ")
                    produccion = readln().toInt()

                    print("Ingresa el nombre del encargado (presiona Enter si no tiene): ")
                    val inputEncargado = readln()
                    val encargado = if (inputEncargado.isNotBlank()) inputEncargado else null

                    CentroCultivo(id, nombre, ubicacion, produccion, encargado).apply {
                        gestorCentros.Agregar(this)
                    }.also {
                        println("Acción finalizada: El centro ${it.nombre} ha sido procesado.")
                    }
                } catch (e: NumberFormatException) {
                    println("Error: El ID y la producción en toneladas deben ser números enteros. Registro cancelado.")
                }
            }
            2 -> {
                if (gestorCentros.Lista.isEmpty()) {
                    println("No hay datos ingresados")
                } else {
                    println("Lista de centros de cultivos:")
                    gestorCentros.Lista.forEach { centro ->
                        print("ID: ${centro.id} | Nombre: ${centro.nombre} | Ubicación: ${centro.ubicacion} | Prod: ${centro.produccionToneladas} ton. | Encargado: ")
                        centro.encargado?.let {
                            println(it)
                        } ?: println("Encargado no asignado")
                    }
                }
            }
            3 -> {
                try {
                    print("Ingresa el ID del centro a buscar: ")
                    val idBuscar = readln().toInt()

                    gestorCentros.Buscar(idBuscar)?.let { centroEncontrado ->
                        println("Centro encontrado: ${centroEncontrado.nombre} ubicado en ${centroEncontrado.ubicacion}")
                    }
                } catch (e: NumberFormatException) {
                    println("Error: Debes ingresar un número válido para el ID.")
                }
            }
            4 -> {
                try {
                    print("Ingresa el ID del centro para consultar al servidor: ")
                    val idServidor = readln().toInt()

                    gestorCentros.Buscar(idServidor)?.let { centroEncontrado ->
                        runBlocking {
                            ServidorSalmonera.consultarProducto(centroEncontrado)
                        }
                    }
                } catch (e: NumberFormatException) {
                    println("Error: Debes ingresar un número válido para el ID.")
                }
            }
            5 -> println("Saliendo del sistema AustralMar. ¡Hasta pronto!")
            else -> println("pción inválida. Por favor, ingresa un número del 1 al 5.")
        }
    } while (opcion != 5)
}