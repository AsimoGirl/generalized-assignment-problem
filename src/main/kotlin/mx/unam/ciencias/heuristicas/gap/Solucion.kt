package mx.unam.ciencias.heuristicas.gap
import kotlin.random.Random


/**
 * Clase Solucion que guarda las posibles soluciones al problema
 *
 * @property grafica La grafica del problema
 * @property asignaciones Las asignaciones de tareas a trabajadores de la solución
 * @property random La semilla para generar valores aleatorios
 * @constructor Crea una solución
 */
class Solucion(val grafica: Grafica, var asignaciones: Array<Int>, private val random: Random) {
    /** Lista de trabajadores del problema*/
    private val trabajadores = grafica.trabajadores
    /** Lista de tareas del problema*/
    private val tareas = grafica.tareas
    /** Boolean si la solución es factible o no*/
    val factible = grafica.esFactible(asignaciones)
    /** Costo de la solución*/
    val costo = grafica.calculaCosto(asignaciones)

    /**
     * Función que obtiene el vecino de una solución, intercambiamos las tareas de dos trabajadores
     * @return Regresa una nueva solución, con los indices intercambiados
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
     * Función que obtiene el vecino de una solución, asignando una tarea a un nuevo trabajador de forma aleatoria
     * @return Regresa una nueva solución, con las asignaciones modificadas
     * */
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
     * Función que muestra la solución
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