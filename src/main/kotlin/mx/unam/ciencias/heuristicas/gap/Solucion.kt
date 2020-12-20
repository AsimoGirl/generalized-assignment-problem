package mx.unam.ciencias.heuristicas.gap
import kotlin.random.Random


/**
 * Clase Solucion
 *
 * @property grafica
 * @property asignaciones
 * @property random La semilla para generar valores aleatorios
 * @constructor Crea una solución
 */
class Solucion(val grafica: Grafica, var asignaciones: Array<Int>, private val random: Random) {
    /** Gráfica que estaremos utilizando*/
    private val trabajadores = grafica.trabajadores
    /** Gráfica que estaremos utilizando*/
    private val tareas = grafica.tareas
    /** Gráfica que estaremos utilizando*/
    val factible = grafica.esFactible(asignaciones)
    /** Gráfica que estaremos utilizando*/
    val costo = grafica.calculaCosto(asignaciones)

    /**
     * Función que obtiene el vecino de una solución, intercambiamos las tareas de dos trabajadores
     * @return Regresa el par de índices del nuevo trabajador con su tarea
     * */
    fun generaVecinoSwap(): Solucion {
        var tareaAleatoria1 = (asignaciones.indices).random(random)
        val tareaAleatoria2 = (asignaciones.indices).random(random)
        while (asignaciones[tareaAleatoria1] == asignaciones[tareaAleatoria2]) {
            tareaAleatoria1 = (asignaciones.indices).random(random)
        }
        val idTrabajador1 = asignaciones[tareaAleatoria1]
        val idTrabajador2 = asignaciones[tareaAleatoria2]
        val vecino = asignaciones
        vecino[tareaAleatoria1] = idTrabajador2
        vecino[tareaAleatoria2] = idTrabajador1
        return Solucion(grafica, vecino, random)
    }

    /**
     * Función que cambia la forma en que se muestra la ruta de la solución
     * @return Una string que representa la solución
     */
    fun generaVecinoShift(): Solucion {
        val tareaAleatoria = (asignaciones.indices).random(random)
        val idviejoTrabajador = asignaciones[tareaAleatoria]
        var idnuevoTrabajador = asignaciones[tareaAleatoria]
        while (idviejoTrabajador == idnuevoTrabajador) {
            idnuevoTrabajador = (0 until trabajadores.size).random(random)
        }
        val vecino = asignaciones
        vecino[tareaAleatoria] = idnuevoTrabajador
        return Solucion(grafica, vecino, random)
    }

    /**
     * Función que cambia la forma en que se muestra la ruta de la solución
     * @return Una string que representa la solución
     */
    override fun toString(): String {
        var solucion = ""
        for(trabajador in trabajadores){
            solucion += "\n Trabajador " + trabajador.id + " -> "
            for(i in asignaciones.indices){
                if(asignaciones[i] + 1 == trabajador.id) {
                    val tarea = tareas.find { it.id == i + 1 }
                    solucion += "Tarea:" + tarea!!.id + " , "
                }
            }
        }
        return solucion
    }
}