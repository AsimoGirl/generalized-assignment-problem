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

    fun calculaCosto(asignaciones: ArrayList<Int>):Double{
        var costoTotal = 0.0
        for (i in 0 until asignaciones.size){
            var idTarea = i + 1
            var idTrabajador = asignaciones[i]
            var costoAux= tablaCostos[idTrabajador-1][idTarea-1]
            costoTotal += costoAux
        }
        if (esFactible(asignaciones)) {
            return costoTotal
        }
            return costoTotal * 10000
    }

    fun calculaMejorTrabajador(id_tarea:Int): ArrayList<Pair<Int, Int>>{
        val mejoresTrabajadoresIds = ArrayList<Pair<Int, Int>>()
        val mejoresTrabajadores = ArrayList<Pair<Pair<Int, Int>, Double>>()
        for(i in 0 until numTrabajadores){
            var idTrabajador = i
            var valorCosto = tablaCostos[idTrabajador][id_tarea]
            var ids = Pair(idTrabajador, id_tarea)
            var par = Pair(ids, valorCosto)
            mejoresTrabajadores[i] = par
        }
        mejoresTrabajadores.sortedWith(compareBy({ it.second }, { it.second }))
        for(i in 0 until mejoresTrabajadores.size){
            mejoresTrabajadoresIds[i] = mejoresTrabajadores[i].first
        }
        return mejoresTrabajadoresIds
    }

    fun revisaCapacidad(id_trabajador: Int, asignaciones: ArrayList<Int>): Boolean {
        var capacidad = 0.0
        for(i in 0 until asignaciones.size) {
            if(asignaciones[i] == id_trabajador) {
                capacidad += tablaCapacidades[id_trabajador - 1][i]
            }
        }
        val trabajador = trabajadores.find{it.id == id_trabajador}
        val capacidadTrabajador = trabajador!!.capacidad
        return capacidad <= capacidadTrabajador
    }

    fun esFactible(asignaciones: ArrayList<Int>): Boolean{
        for(i in 0 until asignaciones.size){
            var idTrabajador = asignaciones[i]
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

    fun generaAsignacionInicial(): ArrayList<Int>{
        val asignacionInicial = ArrayList<Int>()
        var esAdmisible = false
        val tareasIds = ArrayList<Int>()
        for(i in 0 until numTareas){
            tareasIds[i] = i
        }
        for(i in 0 until 100){
            tareasIds.shuffle(Random(42))
            for(i in 0 until numTareas){
                var idTarea = tareasIds[i]
                var candidatos = calculaMejorTrabajador(idTarea)
            }
        }
        if(!esAdmisible){
            for(i in 0 until numTareas){
                asignacionInicial[i] = (0 until trabajadores.size).random()
            }
        }
        return asignacionInicial
    }
}