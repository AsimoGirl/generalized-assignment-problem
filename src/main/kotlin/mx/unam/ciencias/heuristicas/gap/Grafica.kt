package mx.unam.ciencias.heuristicas.gap

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*
import kotlin.random.Random

/**
 * Declaramos nuestra clase Grafica
 * * @constructor Devuelve una gráfica
 */
class Grafica(val trabajadores: ArrayList<Trabajador>, val tareas: ArrayList<Tarea>){
    val numTrabajadores = trabajadores.size
    val numTareas = tareas.size
    val asignacionesTotales = numTrabajadores * numTareas
    val tablaCapacidades =  creaTablaCapacidades()
    val tablaCostos = creaTablaCostos()
    val capacidadTotal = calculaCapacidadTotal()
    val tablaTrabajadoresCap = creaTablaTrabajadoresCap()

    private fun creaTablaCapacidades():Array<DoubleArray>{
        val tablaCapacidades = Array(numTrabajadores) { DoubleArray(numTareas) }
        val capacidades = DAO().getCapacidades()
        for (i in 0 until asignacionesTotales){
            var idTarea = capacidades[i].first.first -1
            var idTrabajador = capacidades[i].first.second -1
            tablaCapacidades[idTrabajador][idTarea] = capacidades[i].second
        }
        return tablaCapacidades
    }

    private fun creaTablaTrabajadoresCap():Array<Double>{
        val tablaTrabajadoresCap = Array(numTrabajadores){0.0}
        for (i in 0 until numTrabajadores){
            tablaTrabajadoresCap[i] = trabajadores[i].capacidad
        }
        return tablaTrabajadoresCap
    }
    private fun creaTablaCostos():Array<DoubleArray>{
        val tablaCostos = Array(numTrabajadores) { DoubleArray(numTareas) }
        val costos = DAO().getCostos()
        for (i in 0 until asignacionesTotales){
            var idTarea = costos[i].first.first -1
            var idTrabajador = costos[i].first.second -1
            tablaCostos[idTrabajador][idTarea] = costos[i].second
        }
        return tablaCostos
    }

    fun calculaCosto(asignaciones: Array<Int>):Double{
        var costoTotal = 0.0
        for (idTarea in asignaciones.indices){
            var idTrabajador = asignaciones[idTarea]
            var costoAux= tablaCostos[idTrabajador][idTarea]
            costoTotal += costoAux
        }
        return costoTotal
    }


    fun revisaCapacidad(id_trabajador: Int, asignaciones: Array<Int>): Boolean {
        var capacidad = 0.0
        for(i in asignaciones.indices) {
            if(asignaciones[i] == id_trabajador) {
                capacidad += tablaCapacidades[id_trabajador][i]
            }
        }
        val capacidadTrabajador = tablaTrabajadoresCap[id_trabajador]
        return capacidad <= capacidadTrabajador
    }

    fun esFactible(asignaciones: Array<Int>): Boolean{
        for(element in asignaciones){
            var idTrabajador = element
            //Revisamos que el trabajador no supere su capacidad
            if (!revisaCapacidad(idTrabajador, asignaciones)){
                return false
            }
        }
        return true
    }

    fun calculaCapacidadTotal(): Double{
        var capacidadTotal = 0.0
        for (trabajador in trabajadores){
            capacidadTotal += trabajador.capacidad
        }
        return capacidadTotal
    }

    fun calculaMejorTrabajador(id_tarea:Int): Array<Int>{
        val mejoresTrabajadores = ArrayList<Pair<Int, Double>>()
        val mejoresTrabajadoresIds = Array<Int>(numTrabajadores){0}
        for(i in 0 until numTrabajadores){
            var idTrabajador = i
            var valorCosto = tablaCostos[idTrabajador][id_tarea]
            var par = Pair(idTrabajador, valorCosto)
            mejoresTrabajadores.add(par)
        }
        mejoresTrabajadores.sortBy { it.second }
        for(i in 0 until mejoresTrabajadores.size){
            mejoresTrabajadoresIds[i] = mejoresTrabajadores[i].first
        }
        return mejoresTrabajadoresIds
    }

    fun generaAsignacionInicial(): Array<Int>{
        val asignacionInicial = Array<Int>(numTareas){0}
        var esAdmisible = false
        val tareasIds = Array<Int>(numTareas){it}
        for(i in 0 until 1000){
            esAdmisible = true
            var caps = Array(numTrabajadores){0.0}
            Collections.shuffle(tareasIds.asList())
            for(a in tareasIds.indices){
                var tarea = tareasIds[a]
                var minTarea = capacidadTotal
                var minTrabajador = -1
                var mejoresCostos = calculaMejorTrabajador(tarea)
                for(i in mejoresCostos){
                    if (caps[i] + tablaCapacidades[i][tarea] <= tablaTrabajadoresCap[i]){
                        if(tablaCapacidades[i][tarea] < minTarea){
                            minTarea = tablaCapacidades[i][tarea]
                            minTrabajador = i
                        }
                    }
                }
                if (minTrabajador == -1){
                    esAdmisible = false
                    break
                }
                else{
                    asignacionInicial[tarea] = minTrabajador
                    caps[minTrabajador] = caps[minTrabajador] + minTarea
                }
            }
            if (esAdmisible)
                break
        }
        if(!esAdmisible){
            for(i in 0 until numTareas){
                asignacionInicial[i] = (0 until numTrabajadores).random()
            }
        }
        return asignacionInicial
    }
}