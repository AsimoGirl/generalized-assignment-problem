package mx.unam.ciencias.heuristicas.tsp

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*

/**
 * Declaramos nuestra clase Grafica
 * * @constructor Devuelve una gráfica
 */
class Grafica(val trabajadores: ArrayList<Trabajador>, val tareas: ArrayList<Tarea>){
    val numTrabajadores = trabajadores.size
    val numTareas = tareas.size
    val aristasTotales = numTrabajadores * numTareas
    val tablaCapacidades =  creaTablaCapacidades()
    val tablaCostos = creaTablaCostos()

    //val numAristas = tablaCapacidades.size * tablaCapacidades[0].size

    private fun creaTablaCapacidades():Array<DoubleArray>{
        val tablaCapacidades = Array(numTrabajadores) { DoubleArray(numTareas) }
        val capacidades = DAO().getCapacidades()
        for (i in 0 until aristasTotales){
            var idTarea = capacidades[i].first.first -1
            var idTrabajador = capacidades[i].first.second -1
            tablaCapacidades[idTrabajador][idTarea] = capacidades[i].second
        }
        return tablaCapacidades
    }

    private fun creaTablaCostos():Array<DoubleArray>{
        val tablaCostos = Array(numTrabajadores) { DoubleArray(numTareas) }
        val costos = DAO().getCostos()
        for (i in 0 until aristasTotales){
            var idTarea = costos[i].first.first -1
            var idTrabajador = costos[i].first.second -1
            tablaCostos[idTrabajador][idTarea] = costos[i].second
        }
        return tablaCostos
    }

    fun calculaCapacidad(aristas: ArrayList<Pair<Int, Int>>):Double{
        val numAristas =aristas.size
        var capacidadTotal = 0.0
        for (i in 0 until numAristas){
            var idTarea = aristas[i].first
            var idTrabajador = aristas[i].second
            capacidadTotal += tablaCapacidades[idTrabajador][idTarea]
        }
        return capacidadTotal
    }

    fun calculaCosto(aristas: ArrayList<Pair<Int, Int>>):Double{
        val numAristas =aristas.size
        var costoTotal = 0.0
        for (i in 0 until numAristas){
            var idTarea = aristas[i].first
            var idTrabajador = aristas[i].second
            costoTotal += tablaCostos[idTrabajador][idTarea]
        }
        return costoTotal
    }
}