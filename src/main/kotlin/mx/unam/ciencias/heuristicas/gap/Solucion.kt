package mx.unam.ciencias.heuristicas.tsp
import kotlin.random.Random
/**
 *
 *
 * @property aristas
 * @constructor Crea una solución
 */
class Solucion(val aristas: ArrayList<Pair<Int,Int>>, private val random: Random) {
    val capacidadTotal = 0
    val costoTotal = 0

    /**
     * Función que obtiene el vecino de una solución, le asignamos una tarea aleatoria a otro trabajador
     * @return Regresa el par de índices del nuevo trabajador con su tarea
     * */
    fun generaVecino(): ArrayList<Pair<Int, Int>> {
        var tareaAleatoria = (0 .. aristas.size).random(random)
        var idviejoTrabajador = aristas[tareaAleatoria].first
        var idnuevoTrabajador = aristas[tareaAleatoria].first

        while (idviejoTrabajador == idnuevoTrabajador) {
            idnuevoTrabajador = (0 .. 500).random(random)
        }
        var vecino = aristas
        vecino[tareaAleatoria].first = idnuevoTrabajador
        return vecino
    }


}