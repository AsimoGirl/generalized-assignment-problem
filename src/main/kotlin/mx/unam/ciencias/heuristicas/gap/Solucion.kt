package mx.unam.ciencias.heuristicas.gap
import kotlin.random.Random
import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import mx.unam.ciencias.heuristicas.gap.Grafica
import sun.font.TrueTypeFont

/**
 *
 *
 * @property asignaciones
 * @constructor Crea una solución
 */
class Solucion(val grafica: Grafica, val asignaciones: ArrayList<Int>, private val random: Random) {
    val trabajadores = grafica.trabajadores
    val tareas = grafica.tareas
    val costo = grafica.calculaCosto(asignaciones)
    val capacidadTotal = grafica.capacidadTotal
    val factible = grafica.esFactible(asignaciones)

    fun generaSolucionInicial(): Solucion{
        var encuentraFactible = true
        val asignaInicial = arrayListOf<Int>()
        for (i in 0 until 1000){
            encuentraFactible = true
            tareas.shuffle(random)
            for (tarea in 0 until tareas.size){
                var capTareas = capacidadTotal
                var minTrabajador = -1
                for (i in 1 until trabajadores.size+1){
                    val trabajador = trabajadores.find{it.id == i}
                    val capacidadTrabajador = trabajador!!.capacidad
                    if (grafica.tablaCapacidades[i-1][tarea] <= capacidadTrabajador){
                        if(grafica.tablaCapacidades[i-1][tarea] < capTareas){
                            capTareas = grafica.tablaCapacidades[i-1][tarea]
                            minTrabajador = i
                        }
                    }
                }
                if (minTrabajador == -1){
                    encuentraFactible = false
                    break
                }
                else{
                    asignaInicial[tarea] = minTrabajador
                }
            }
            if (encuentraFactible){
                break
            }
        }
        if (!encuentraFactible){
            for (i in 0 until tareas.size){
                asignaInicial[i] = (1 .. trabajadores.size + 1).random(random)
            }
        }
        return Solucion(grafica, asignaInicial, random)
    }
    /**
     * Función que obtiene el vecino de una solución, le asignamos una tarea aleatoria a otro trabajador
     * @return Regresa el par de índices del nuevo trabajador con su tarea
     * */
    fun generaVecinoShift(): ArrayList<Int> {
        var tareaAleatoria = (0 .. asignaciones.size).random(random)
        var idviejoTrabajador = asignaciones[tareaAleatoria]
        var idnuevoTrabajador = asignaciones[tareaAleatoria]
        while (idviejoTrabajador == idnuevoTrabajador) {
            idnuevoTrabajador = (1 .. trabajadores.size + 1).random(random)
        }
        var vecino = asignaciones
        vecino[tareaAleatoria] = idnuevoTrabajador
        return vecino
    }

    /**
     * Función que obtiene el vecino de una solución, intercambiamos las tareas de dos trabajadores
     * @return Regresa el par de índices del nuevo trabajador con su tarea
     * */
    fun generaVecinoSwap(): ArrayList<Int> {
        var tareaAleatoria1 = (0 .. asignaciones.size).random(random)
        var tareaAleatoria2 = (0 .. asignaciones.size).random(random)
        while (asignaciones[tareaAleatoria1] == asignaciones[tareaAleatoria2]) {
            tareaAleatoria1 = (1 .. trabajadores.size + 1).random(random)
        }
        var idTrabajador1 = asignaciones[tareaAleatoria1]
        var idTrabajador2 = asignaciones[tareaAleatoria2]
        var vecino = asignaciones
        vecino[tareaAleatoria1] = idTrabajador2
        vecino[tareaAleatoria2] = idTrabajador1
        return vecino
    }

    /**
     * Función que cambia la forma en que se muestra la ruta de la solución
     * @return Una string que representa la solución
     */
    override fun toString(): String {
        var solucion = ""
        for(i in 0 until asignaciones.size){
            var tarea = tareas.find{it.id == i+1}
            var trabajador = trabajadores.find{it.id == asignaciones[i]}
            solucion +=
                "Tarea:" +  tarea!!.nombre + " -> Trabajador:" + trabajador!!.nombre + "\n"
        }
        return solucion
    }
}