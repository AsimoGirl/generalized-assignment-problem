package mx.unam.ciencias.heuristicas.gap
import kotlin.random.Random
import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import mx.unam.ciencias.heuristicas.gap.Grafica
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *
 * @property asignaciones
 * @constructor Crea una solución
 */
class Solucion(val grafica: Grafica, var asignaciones: Array<Int>, private val random: Random) {
    val trabajadores = grafica.trabajadores
    val tareas = grafica.tareas
    val capacidadTotal = grafica.capacidadTotal
    val factible = grafica.esFactible(asignaciones)


    /**
     * Función que obtiene el vecino de una solución, intercambiamos las tareas de dos trabajadores
     * @return Regresa el par de índices del nuevo trabajador con su tarea
     * */
    fun generaVecinoSwap(): Solucion {
        var tareaAleatoria1 = (0 until asignaciones.size).random(random)
        var tareaAleatoria2 = (0 until asignaciones.size).random(random)
        while (asignaciones[tareaAleatoria1] == asignaciones[tareaAleatoria2]) {
            tareaAleatoria1 = (1 .. trabajadores.size).random(random)
        }
        var idTrabajador1 = asignaciones[tareaAleatoria1]
        var idTrabajador2 = asignaciones[tareaAleatoria2]
        var vecino = asignaciones
        vecino[tareaAleatoria1] = idTrabajador2
        vecino[tareaAleatoria2] = idTrabajador1
        return Solucion(grafica, vecino, random)
    }

    fun getCosto(): Double{
        return grafica.calculaCosto(asignaciones)
    }

    /**
     * Función que cambia la forma en que se muestra la ruta de la solución
     * @return Una string que representa la solución
     */
    override fun toString(): String {
        var solucion = ""
        for(i in asignaciones.indices){
            var tarea = tareas.find{it.id == i+1}
            var trabajador = trabajadores.find{it.id == asignaciones[i] + 1}
            solucion +=
                "Tarea:" +  tarea!!.id + " -> Trabajador:" + trabajador!!.id + "\n"
        }
        return solucion
    }
}